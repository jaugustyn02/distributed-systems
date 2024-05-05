import newsletter_pb2_grpc
import newsletter_pb2
import grpc
import hashlib
import time
import threading
import signal
import sys


citiesCode = {"NY": "New York", "SF": "San Francisco", "CH": "Chicago", "LA": "Los Angeles", "BO": "Boston", "AU": "Austin"}
tagsCode = {"S": "Sports", "P": "Politics", "T": "Technology", "B": "Business", "E": "Entertainment"}
notificationTypes = {0: "New Event", 1: "Event update", 2: "Event cancellation"}

def hash(s):
    return int(hashlib.sha1(s.encode("utf-8")).hexdigest(), 16) % (10 ** 8)

def print_notification(notification):
    msg = "<New Notification>: "
    msg += f"Title: {notification.title}, "
    msg += f"Description: {notification.description}, "
    msg += f"Type: {notificationTypes[notification.type]}, "
    msg += f"City: {notification.city}, "
    msg += f"Tags: {', '.join(notification.tags)}"
    print('\n' + msg, end='')

def print_response(response):
    print("<Response>: ", end='')
    print(f"Success: {response.success}", end=', ')
    print(f"Message: {response.message}")

class NewsletterClient:
    def __init__(self, username, host, port, reconnect_backoff=5):
        self.username = username
        self.user_id = hash(username)
        self.reconnect_backoff = reconnect_backoff
        print(f'User ID: {self.user_id}')
        
        channel_options = [
            ('grpc.keepalive_time_ms', 60000),
            ('grpc.keepalive_timeout_ms', 20000),
            ('grpc.keepalive_permit_without_calls', True)
        ]
        self.channel = grpc.insecure_channel(f'{host}:{port}', options=channel_options)
        self.stub = newsletter_pb2_grpc.NewsletterServiceStub(self.channel)

    def start(self):
        self.running = True
        self.interruped = False
        self.reader_thread = threading.Thread(target=self.read_notifications)
        self.reader_thread.start()

    def subscribe(self, city, tags):
        request = newsletter_pb2.SubscriptionRequest(userId=self.user_id, city=city, tags=tags)
        response = None
        try:
            response = self.stub.Subscribe(request)
        except grpc.RpcError as e:
            sys.stderr.write(f"[Error: {e.code()}]: Failed to subscribe\n")
        return response

    def unsubscribe(self):
        request = newsletter_pb2.UserRequest(userId=self.user_id)
        response = None
        try:
            response = self.stub.Unsubscribe(request)
        except grpc.RpcError as e:
            sys.stderr.write(f"[Error: {e.code()}]: Failed to unsubscribe\n")
        return response

    def get_notifications(self):
        request = newsletter_pb2.UserRequest(userId=self.user_id)
        response = self.stub.GetNotifications(request)
        return response
    
    def is_connection_alive(self):
        state = self.channel._channel.check_connectivity_state(True)
        return state == 2 # 2 is READY

    
    def read_notifications(self):
        connected = True
        while self.running:
            while self.interruped and self.running:
                time.sleep(1)
            try:
                if not connected and self.is_connection_alive():
                    sys.stderr.write("\n[Info]: Reconnected")
                    connected = True
                for notification in self.get_notifications():
                    while self.interruped and self.running:
                        time.sleep(1)
                    print_notification(notification)
            except grpc.RpcError as e:
                if self.running:
                    connected = False
                    sys.stderr.write(f"\n[Error: {e.code()}]: Attempting to reconnect...")
                    time.sleep(self.reconnect_backoff)
            except Exception as e:
                sys.stderr.write(f"\n[Error]: {e}")
                time.sleep(self.reconnect_backoff)

    def stop(self):
        self.interruped = False
        self.running = False
        self.channel.close()
        self.reader_thread.join()
        
def console(client):
    def print_options():
        print("Options:")
        print("1. Subscribe")
        print("2. Unsubscribe")
        print("3. Show options")
        print("4. Exit")

    print_options()
    while True:
        try:
            choice = input("<Enter choice>: ")

            if choice == '1' or choice == 's':
                print("Available cities :" + ', '.join([f"{code} ({city})" for code, city in citiesCode.items()]))
                city = citiesCode.get(input("<Enter city>: ").strip().upper(), "")
                print("Available tags :" + ', '.join([f"{code} ({tag})" for code, tag in tagsCode.items()]))
                tags = input("<Enter tags> (comma separated): ").split(',')
                tags = [tagsCode.get(tag.strip().upper()) for tag in tags if tagsCode.get(tag.strip().upper())]
                response = client.subscribe(city, tags)
                if response:
                    print_response(response)
            elif choice == '2' or choice == 'u':
                response = client.unsubscribe()
                if response:
                    print_response(response)
            elif choice == '3' or choice in ('o', 'h'):
                print_options()
            elif choice == '4' or choice in ('e', 'q'):
                print("Exiting...")
                client.stop()
                exit(0)
            else:
                if choice:
                    print("Invalid choice")
        except Exception as e:
            print(f"Console Error: {e}")
        
def main():
    def signal_handler(sig, frame):
        client.interruped = not client.interruped
        if client.interruped:
            print("\nClient paused: Press 'Ctrl+C' to resume", end='')
        else:
            print('\nClient resumed', end='')

    print("Welcome to the Newsletter Service")
    username = input("Enter username: ")
    client = NewsletterClient(username, 'localhost', 50051)
    
    signal.signal(signal.SIGINT, signal_handler)

    client.start()
    console(client)


if __name__ == '__main__':
    main()