from fastapi import FastAPI, Request, Form, HTTPException
from fastapi.responses import HTMLResponse, JSONResponse
from dotenv import load_dotenv
import os

from fetches import fetch_imdb_find_title, fetch_imdb_data, fetch_mdblist_ratings


load_dotenv()

app = FastAPI()

# RapidAPI keys
rapidapi_api_key = os.getenv("RAPIDAPI_API_KEY")


# Exception handler
@app.exception_handler(HTTPException)
async def exception_handler(request, exc):
    status_code = exc.status_code
    detail = exc.detail

    return HTMLResponse(
        f"""
        <html>
        <head>
        <title>Error :/</title>
        </head>
        <body>
            <h1>Error {status_code}</h1>
            <p>{detail}</p>
            <a href="/"><button>Go back to home page</button></a>
        </body>
        </html>
        """
    )


# Root endpoint
@app.get("/")
async def root():
    return HTMLResponse(
        """
        <html>
        <head>
        <title>Movie Ratings Service</title>
        </head>
        <body>
            <h1>Welcome {user_name}! </h1>
            <p>This is one of the API service that allows you to search for a Movie or TV-Series ratings from different sources.</p>
            <a href="/search"><button>Start searching</button></a>
        </body>
        </html>
        """
    )


# Movie rate endpoint - JSON response
@app.post("/ratings_raw", response_class=JSONResponse)
async def ratings_raw(request: Request, title = Form(None), api_key = Form(None)):
    if not api_key or api_key != rapidapi_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    if not title:
        raise HTTPException(status_code=400, detail="Title cannot be empty")

    imdb_title_data = await fetch_imdb_find_title(title, rapidapi_api_key)
    if not imdb_title_data:
        raise HTTPException(status_code=404, detail="Movie not found")

    imdb_title_id = imdb_title_data.get("id")
    imdb_rating, avg_rating, ratings = None, None, {}
    if imdb_title_id:
        imdb_rating = await fetch_imdb_data(imdb_title_id, rapidapi_api_key)
        ratings = await fetch_mdblist_ratings(imdb_title_id, rapidapi_api_key)

        if imdb_rating:
            ratings["imdb"] = imdb_rating
        avg_rating = float("{:.2f}".format(sum(ratings.values()) / len(ratings)))

    return {
        "title": imdb_title_data["title"],
        "year": imdb_title_data["year"],
        "poster_url": imdb_title_data["poster_url"],
        "type": imdb_rating["type"],
        "genres": imdb_rating["genres"],
        "avg_rating": avg_rating,
        "ratings": ratings,
    }


# Movie rate endpoint - HTML response
@app.post("/ratings", response_class=JSONResponse)
async def ratings(request: Request, title = Form(None), api_key = Form(None)):
    if not api_key or api_key != rapidapi_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    if not title:
        raise HTTPException(status_code=400, detail="Title cannot be empty")

    imdb_find_data = await fetch_imdb_find_title(title, rapidapi_api_key)
    if not imdb_find_data:
        raise HTTPException(status_code=500, detail="Could not fetch movie data or movie not found")

    imdb_title_id = imdb_find_data.get("id")
    imdb_data, avg_rating, ratings = None, None, {}
    if imdb_title_id:
        imdb_data = await fetch_imdb_data(imdb_title_id, rapidapi_api_key)
        ratings = await fetch_mdblist_ratings(imdb_title_id, rapidapi_api_key)

        if imdb_data:
            ratings["imdb"] = imdb_data["rating"]
        avg_rating = float("{:.2f}".format(sum(ratings.values()) / len(ratings)))

    sorted_ratings = dict(sorted(ratings.items(), key=lambda x: x[1], reverse=True))
    ratings_list = ''.join([f"<li>{source}: {rating}</li>" for source, rating in sorted_ratings.items()])
    return HTMLResponse(
        f"""
        <html>
        <head>
        <title>Movie Ratings - {imdb_find_data["title"]}</title>
        </head>
        <body>
            <h1 style="font-weight: normal;">Ratings for:&nbsp;<strong>{imdb_find_data["title"]}</strong></h1>
            <img src="{imdb_find_data["poster_url"]}" alt="{imdb_find_data["title"]}" + " - poster" style="max-width: 300px; max-height: 400px;">
            <p> Release Date: {imdb_find_data["year"]}</p>
            <p> Type: {imdb_data["type"]}</p>
            <p> Genres: {imdb_data["genres"]}</p>
            <h2>Average rating: {avg_rating}</h2>
            <p>Ratings:</p>
            <ul>
                {ratings_list}
            </ul>
            <a href="/search"><button>Search again</button></a>
        </body>
        </html>
        """
    )

# Form endpoint
@app.get("/search", response_class=HTMLResponse)
async def search(request: Request):
    return """
    <html>
        <head>
            <title>Search</title>
        </head>
        <body>
            <h1>Search for a movie or TV-series ratings</h1>
            <form method="post" action="/ratings" style="display: flex; flex-direction: column; width: 400px;">
                <label for="api_key">Enter API key</label><br>
                <input type="text" id="api_key" name="api_key" placeholder="a1b2c3d4e5f6g..."><br>
                <label for="title">Enter a title</label><br>
                <input type="text" id="title" name="title" placeholder="eg. Toy Story 2"><br>
                <input type="submit" value="Search">
            </form>
            <br>
            <br>
            <a href="/"><button>Go back to home page</button></a>
        </body>
    </html>
    """
