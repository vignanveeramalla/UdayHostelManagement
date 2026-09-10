const COMPLAINT_API =
    "https://udayhostelmanagement-production.up.railway.app/complaints";


const complaintForm =
    document.getElementById(
        "publicComplaintForm"
    );


complaintForm.addEventListener(
    "submit",
    submitComplaint
);


// ==========================================
// SUBMIT PUBLIC COMPLAINT
// ==========================================

async function submitComplaint(event)
{

    event.preventDefault();


    const studentId =
        Number(
            document.getElementById(
                "studentId"
            ).value
        );


    const subject =
        document.getElementById(
            "subject"
        ).value.trim();


    const description =
        document.getElementById(
            "description"
        ).value.trim();



    // ======================================
    // VALIDATION
    // ======================================

    if (!studentId)
    {
        showComplaintMessage(
            "Please enter a valid Student ID.",
            false
        );

        return;
    }


    if (subject === "")
    {
        showComplaintMessage(
            "Please enter complaint subject.",
            false
        );

        return;
    }


    if (description === "")
    {
        showComplaintMessage(
            "Please enter complaint description.",
            false
        );

        return;
    }



    const complaintData =
    {
        studentId: studentId,

        subject: subject,

        description: description
    };


    console.log(
        "Submitting complaint:",
        complaintData
    );


    const button =
        document.getElementById(
            "submitComplaintButton"
        );


    button.disabled = true;

    button.textContent =
        "Submitting...";


    try
    {

        const response =
            await fetch(
                COMPLAINT_API,
                {
                    method: "POST",

                    headers:
                    {
                        "Content-Type":
                            "application/json"
                    },

                    body:
                        JSON.stringify(
                            complaintData
                        )
                }
            );


        const result =
            await response.json();


        console.log(
            "Complaint response:",
            result
        );


        if (response.ok)
        {

            showComplaintMessage(
                result.message ||
                "Complaint submitted successfully.",
                true
            );


            complaintForm.reset();

        }
        else
        {

            showComplaintMessage(
                result.message ||
                "Unable to submit complaint.",
                false
            );

        }

    }
    catch(error)
    {

        console.error(
            "Complaint error:",
            error
        );


        showComplaintMessage(
            "Unable to connect to server.",
            false
        );

    }
    finally
    {

        button.disabled = false;

        button.textContent =
            "📢 Submit Complaint";

    }

}



// ==========================================
// SHOW MESSAGE
// ==========================================

function showComplaintMessage(
    message,
    success
)
{

    const messageBox =
        document.getElementById(
            "complaintMessage"
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