project name: Flight Control
project description: In this game you will coordinate airplanes to the airstrips
command: 
    -Alexander Movsesov
    -Alexander Kuznetsov
    -Sergey Malyshev
apis description: This api is use for send game states to the server
    Api use REST principles and working with json
    Methods:
        register(name, email, pass) Register an user, if its not contains in the HashMap| Restrictions: user must be logouted
        change(name, email, pass) Changing users data, Restrictions: user must be logged in
        get()| Restrictions: works only if user is logged in| also this method supports CORS.
        authenticate(name, pass, repass) Make users authentication| Restrictions: user must be logged out
        logout()| Restrictions: user must be logged in