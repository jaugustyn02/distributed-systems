import requests

url = "https://imdb146.p.rapidapi.com/v1/find/"

querystring = {"query":"iron man"}

headers = {
	"X-RapidAPI-Key": "9c7ba28206msh67bbb3d92ee3e7fp16c0fajsn6fe8faf64093",
	"X-RapidAPI-Host": "imdb146.p.rapidapi.com"
}

response = requests.get(url, headers=headers, params=querystring)

print(response.json())