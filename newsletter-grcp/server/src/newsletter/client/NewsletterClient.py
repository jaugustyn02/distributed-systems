import newsletter_pb2_grpc
import newsletter_pb2
import grpc
import hashlib

class NewsletterClient:
    def __init__(self, username, host, port):
        self.username = username
        self.user_id = hash(username)
        print(f'User ID: {self.user_id}')
        self.channel = grpc.insecure_channel(f'{host}:{port}')
        self.stub = newsletter_pb2_grpc.NewsletterServiceStub(self.channel)

    def subscribe(self, city, tags):
        request = newsletter_pb2.SubscriptionRequest(userId=self.user_id, city=city, tags=tags)
        response = self.stub.Subscribe(request)
        return response

    def unsubscribe(self):
        request = newsletter_pb2.UserRequest(userId=self.user_id)
        response = self.stub.Unsubscribe(request)
        return response

    def get_notifications(self):
        request = newsletter_pb2.UserRequest(userId=self.user_id)
        response = self.stub.GetNotifications(request)
        return response

    def __del__(self):
        self.channel.close()
    
def hash(s):
    # hash string s to a 8-digit integer
    return int(hashlib.sha1(s.encode("utf-8")).hexdigest(), 16) % (10 ** 8)

def main():
    username = input("Enter username: ")
    client = NewsletterClient(username, 'localhost', 50051)
    # response = client.subscribe('New York', ['Sports', 'Business', 'Science', 'Health', 'Entertainment'])
    # print(response)

    notifications = client.get_notifications()
    # print(response)

    for notification in notifications:
        print(notification)

    # response = client.unsubscribe()
    # print(response)


if __name__ == '__main__':
    main()