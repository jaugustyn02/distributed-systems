from fastapi import FastAPI
from enum import Enum

app=FastAPI( )


class Poll():
    def __init__(self, name, pole_id):
        self.name = name
        self.pole_id = pole_id
        self.items = []

    def add_item(self, item):
        self.items.append(item)

    def get_items(self):
        return self.items


@app.get("/")
async def root() :
    return {"message" : "Hello and welcome to the Los Polls Hermanos family!"}


@app.get("/hello/{name}")
async def say_hello(name: str) :
    return {"message" : f"Hello {name}"}


@app.get("/polls/create")
async def create_poll() :
    return {"message" : "Create a poll"}


@app.get("/polls/{poll_id}")
async def get_poll(poll_id: int) :
    return {"message" : f"Get poll with id {poll_id}"}


# add an item to a poll
@app.post("/polls/{poll_id}/add")
async def add_item_to_poll(poll_id: int):
    return {"message" : f"Add item to poll with id {poll_id}"}


# delete an item from a poll
@app.post("/polls/{poll_id}/delete")
async def delete_item_from_poll(poll_id: int):
    return {"message" : f"Delete item from poll with id {poll_id}"}


@app.get("/polls/{poll_id}/results")
async def get_poll_results(poll_id: int) :
    return {"message" : f"Get poll results with id {poll_id}"}


@app.get("/polls/{poll_id}/vote")
async def vote_poll(poll_id: int) :
    return {"message" : f"Vote on poll with id {poll_id}"}


@app.get("/polls/{poll_id}/delete")
async def delete_poll(poll_id: int) :
    return {"message" : f"Delete poll with id {poll_id}"}


@app.get("/polls/{poll_id}/edit")
async def edit_poll(poll_id: int) :
    return {"message" : f"Edit poll with id {poll_id}"}


# create a poll with a post request
@app.post("/polls/create")
async def create_poll(post_name: str) :
    return {"message" : f"Create a poll with name {post_name}"}

@app.post("/polls/{poll_id}/edit")
async def edit_poll(poll_id: int) :
    return {"message" : f"Edit poll with id {poll_id}"}