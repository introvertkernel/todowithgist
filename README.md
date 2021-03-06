# TodoWithGist
[![codecov](.github/badges/jacoco.svg)](https://github.com/introvertkernel/todowithgist)

A Demo Todo Application with Github authentication and gist export feature

**This project is hosted with Heroku at https://todowithgist.herokuapp.com**

### Below listed are the APIs in this project
1. POST /api/user/projects/add : To add new project
2. GET /api/user/projects : To fetch all projects of a authenticated user
3. DELETE /api/user/projects : To delete a project
4. GET /api/user/projects/todo : Fetch all todos of a authenticated user
5. DELETE /api/user/projects/todo : Delete a todo
6. GET /api/user/gist : Export a project to private Gist
