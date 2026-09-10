const loginForm=document.getElementById("loginForm");
const message=document.getElementById("message");

loginForm.addEventListener("submit",async function(event)
{
    event.preventDefault();

    const username=document.getElementById("username").value.trim();
    const password=document.getElementById("password").value;

    //Frontend validation
    if(username==="")
    {
        message.textContent="Email is required";
        return;
    }

    if(password==="")
    {
        message.textContent="Password is required";
        return;
    }

    try
    {
        const response=await fetch("https://udayhostelmanagement-production.up.railway.app/auth/login",
            {
                method: "POST",

                headers: 
                {
                    "Content-Type":"application/json"
                },
                body:JSON.stringify
                ({
                    username:username,
                    password:password
                })
            });

            const result=await response.json();

            console.log("Server response:",result);

            if(response.ok)
            {
                //Save JWT token
                localStorage.setItem("token",result.data);

                message.textContent="Login successful!";

                //Go to dashboard
                window.location.href="dashboard.html";
            }
            else
            {
                //Show validation errors
                if(result.data)
                {
                    let errors="";

                    for(const field in result.data)
                    {
                        errors+=result.data[field]+" ";
                    }
                    message.textContent=errors;
                }
                else
                {
                message.textContent=result.message;
                }
            }
    }
    catch (error)
    {
        console.error(error);

        message.textContent="Unable to connect to server.";
    }
});