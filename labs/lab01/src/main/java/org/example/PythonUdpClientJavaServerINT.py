import socket;

serverIP = "127.0.0.1"
serverPort = 9008
msg = "Żółta Gęś"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
#client.sendto(bytes(msg, 'cp1250'), (serverIP, serverPort))
# client.sendto(bytes(msg, 'utf-8'), (serverIP, serverPort))
msg_bytes = (300).to_bytes(4, byteorder='little')
client.sendto(msg_bytes, (serverIP, serverPort))

buffer, address = client.recvfrom(1024)
print("FROM SERVER: ", int(from_bytes(buffer, byteorder="little")))



