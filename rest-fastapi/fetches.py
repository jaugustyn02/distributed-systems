import httpx
from fastapi import FastAPI, HTTPException

# MDBList ranking sources
mdblist_score_100_sources = ["metacritic", "tomatoes", "metacriticuser", "trakt", "tomatoesaudience", "tmdb", "letterboxd"]
mdblist_value_4_sources = ["rogerebert"]


# Fetch IMDb title ID asynchronously
async def fetch_imdb_find_title(title_input: str, rapidapi_api_key: str):
    url = "https://imdb146.p.rapidapi.com/v1/find/"
    querystring = {"query": title_input}
    headers = {
        "X-RapidAPI-Key": rapidapi_api_key,
        "X-RapidAPI-Host": "imdb146.p.rapidapi.com"
    }
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(url, headers=headers, params=querystring)
            response.raise_for_status()
            data = response.json()
            results = data.get("titleResults").get("results")
            if not results:
                raise HTTPException(status_code=404, detail=f"Movie not found: {title_input}")
            match = results[0]
            id = match.get("id")
            title = match.get("titleNameText", "-")
            year = match.get("titleReleaseText", "-")
            poster_url = match.get("titlePosterImageModel", {}).get("url", "")
            return {
                "id": id,
                "title": title,
                "year": year,
                "poster_url": poster_url
            }
        except (httpx.RequestError, IndexError, KeyError) as e:
            raise HTTPException(status_code=500, detail=f"Error fetching data from IMDB API: {e}")


# Fetch IMDb rating asynchronously
async def fetch_imdb_data(title_id: str, rapidapi_api_key: str):
    url = f"https://imdb146.p.rapidapi.com/v1/title/"
    querystring = {"id": title_id}
    headers = {
        "X-RapidAPI-Key": rapidapi_api_key,
        "X-RapidAPI-Host": "imdb146.p.rapidapi.com"
    }
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(url, headers=headers, params=querystring)
            response.raise_for_status()
            data = response.json()
            ratingsSummary = data.get("ratingsSummary")
            rating = ratingsSummary.get("aggregateRating")
            type = data.get("titleType").get("text", data.get("titleType").get("id", ""))
            if not type: type = "-"
            else: type = type.capitalize()
            genres = ', '.join([genre.get("text") for genre in data.get("genres").get("genres")])
            return {"rating": rating, "type": type, "genres": genres}
        except (httpx.RequestError, KeyError) as e:
            raise HTTPException(status_code=500, detail=f"Error fetching data from IMDB API: {e}")


# Fetch MDBList rating asynchronously
async def fetch_mdblist_ratings(title_id: str, rapidapi_api_key: str):
    url = "https://mdblist.p.rapidapi.com/"
    querystring = {"i":title_id}
    headers = {
        "X-RapidAPI-Key": rapidapi_api_key,
        "X-RapidAPI-Host": "mdblist.p.rapidapi.com"
    }
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(url, headers=headers, params=querystring)
            response.raise_for_status()
            data = response.json()
            ratings_data = data.get("ratings", [])
            ratings = {}
            for rating in ratings_data:
                source = rating.get("source")
                if rating.get("score") is not None and source in mdblist_score_100_sources:
                    ratings[source] = rating.get("score") / 10 # convert 100 scale rating to 10 scale rating
                elif rating.get("value") is not None and source in mdblist_value_4_sources:
                    ratings[source] = rating.get("value") * 2.5 # convert 4-star rating to 10 scale rating
            return ratings
        except (httpx.RequestError, KeyError) as e:
            raise HTTPException(status_code=500, detail=f"Error fetching data from MDBList API: {e}")
