project name: <b>Flight Control</b> <br/>
project description: In this game you will coordinate airplanes to the airstrips<br/>
command:<br/> 
    -Alexander Movsesov<br/>
    -Alexander Kuznetsov<br/>
    -Sergey Malyshev<br/>
apis description: This api is use for send game states to the server <br/>
    Api use REST principles and working with json <br/>
    Methods:<br/>
        register(name, email, pass) Register an user, if its not contains in the HashMap| Restrictions: user must be logouted<br/>
        change(name, email, pass) Changing users data, Restrictions: user must be logged in<br/>
        get()| Restrictions: works only if user is logged in| also this method supports CORS.<br/>
        authenticate(name, pass, repass) Make users authentication| Restrictions: user must be logged out<br/>
        logout()| Restrictions: user must be logged in