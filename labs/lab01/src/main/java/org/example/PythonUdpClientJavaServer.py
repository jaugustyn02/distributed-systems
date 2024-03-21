import socket;

serverIP = "127.0.0.1"
serverPort = 9008
msg = "Żółta Gęś"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
#client.sendto(bytes(msg, 'cp1250'), (serverIP, serverPort))
client.sendto(bytes(msg, 'utf-8'), (serverIP, serverPort))




