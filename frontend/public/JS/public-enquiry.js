const ENQUIRY_API =
    "http://localhost:8080/enquiries";


const enquiryForm =
    document.getElementById(
        "publicEnquiryForm"
    );


enquiryForm.addEventListener(
    "submit",
    submitEnquiry
);


// ==========================================
// SUBMIT ENQUIRY
// ==========================================

async function submitEnquiry(event)
{

    event.preventDefault();


    const name =
        document.getElementById(
            "name"
        ).value.trim();


    const email =
        document.getElementById(
            "email"
        ).value.trim();


    const message =
        document.getElementById(
            "message"
        ).value.trim();



    // ======================================
    // VALIDATION
    // ======================================

    if (name === "")
    {
        showEnquiryMessage(
            "Please enter your name.",
            false
        );

        return;
    }


    if (email === "")
    {
        showEnquiryMessage(
            "Please enter your email.",
            false
        );

        return;
    }


    if (message === "")
    {
        showEnquiryMessage(
            "Please enter your enquiry.",
            false
        );

        return;
    }



    const enquiryData =
    {
        name: name,

        email: email,

        message: message
    };


    console.log(
        "Submitting enquiry:",
        enquiryData
    );


    const button =
        document.getElementById(
            "submitEnquiryButton"
        );


    button.disabled = true;

    button.textContent =
        "Sending...";


    try
    {

        const response =
            await fetch(
                ENQUIRY_API,
                {
                    method: "POST",

                    headers:
                    {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(
                            enquiryData
                        )
                }
            );


        const result =
            await response.json();


        console.log(
            "Enquiry response:",
            result
        );


        if (response.ok)
        {

            showEnquiryMessage(
                result.message ||
                "Enquiry submitted successfully.",
                true
            );


            enquiryForm.reset();

        }
        else
        {

            showEnquiryMessage(
                result.message ||
                "Unable to submit enquiry.",
                false
            );

        }

    }
    catch(error)
    {

        console.error(
            "Enquiry error:",
            error
        );


        showEnquiryMessage(
            "Unable to connect to server.",
            false
        );

    }
    finally
    {

        button.disabled = false;

        button.textContent =
            "✉ Send Enquiry";

    }

}



// ==========================================
// SHOW MESSAGE
// ==========================================

function showEnquiryMessage(
    message,
    success
)
{

    const messageBox =
        document.getElementById(
            "enquiryMessage"
        );


    messageBox.textContent =
        message;


    if (success)
    {

        messageBox.className =
            "success-message";

    }
    else
    {

        messageBox.className =
            "error-message";

    }


    messageBox.scrollIntoView(
        {
            behavior: "smooth",
            block: "center"
        }
    );

}