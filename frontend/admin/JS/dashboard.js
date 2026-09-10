// ================= TOKEN =================

const token = localStorage.getItem("token");

if (!token)
{
    window.location.href = "login.html";
}


// ================= LOAD DASHBOARD =================

async function loadDashboard()
{
    try
    {
        const response = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/dashboard",
            {
                method: "GET",

                headers:
                {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result = await response.json();


        console.log(
            "Dashboard response:",
            result
        );


        if (response.ok)
        {
            const data = result.data;


            // ================= STUDENTS =================

            document.getElementById(
                "totalStudents"
            ).textContent =
                data.totalStudents;


            // ================= ROOMS =================

            document.getElementById(
                "totalRooms"
            ).textContent =
                data.totalRooms;


            document.getElementById(
                "totalOccupiedRooms"
            ).textContent =
                data.totalOccupiedRooms;


            document.getElementById(
                "totalAvailableRooms"
            ).textContent =
                data.totalAvailableRooms;


            // ================= FEES =================

            document.getElementById(
                "totalFees"
            ).textContent =
                "₹" +
                Number(data.totalFees)
                    .toLocaleString("en-IN");


            document.getElementById(
                "totalPaid"
            ).textContent =
                "₹" +
                Number(data.totalPaid)
                    .toLocaleString("en-IN");


            document.getElementById(
                "totalPending"
            ).textContent =
                "₹" +
                Number(data.totalPending)
                    .toLocaleString("en-IN");


            // ================= COMPLAINTS =================

            document.getElementById(
                "totalComplaints"
            ).textContent =
                data.totalComplaints;


            document.getElementById(
                "openComplaints"
            ).textContent =
                data.openComplaints;


            // ================= ENQUIRIES =================

            document.getElementById(
                "pendingEnquiries"
            ).textContent =
                data.pendingEnquiries;

        }
        else
        {
            document.getElementById(
                "message"
            ).textContent =
                result.message ||
                "Unable to load dashboard.";
        }

    }
    catch(error)
    {
        console.error(
            "Dashboard error:",
            error
        );


        document.getElementById(
            "message"
        ).textContent =
            "Unable to connect to server.";
    }
}

// ================= LOAD MONTHLY PAYMENT SUMMARY =================

async function loadMonthlyPaymentSummary()
{
    try
    {
        const today = new Date();

        const year = today.getFullYear();

        const month = today.getMonth() + 1;


        const response = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/payments/monthly-summary"
            + "?year=" + year
            + "&month=" + month,
            {
                method: "GET",

                headers:
                {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result =
            await response.json();


        console.log(
            "Monthly payment summary:",
            result
        );


        if (response.ok)
        {
            const summary = result.data;


            // ================= TITLE =================

            const monthName =
                today.toLocaleString(
                    "en-IN",
                    {
                        month: "long"
                    }
                );


            document.getElementById(
                "monthlyPaymentTitle"
            ).textContent =
                monthName +
                " " +
                year +
                " Payment Overview";


            // ================= MONEY =================

            document.getElementById(
                "monthlyExpectedFee"
            ).textContent =
                "₹" +
                Number(
                    summary.totalExpectedFee
                ).toLocaleString("en-IN");


            document.getElementById(
                "monthlyCollected"
            ).textContent =
                "₹" +
                Number(
                    summary.totalCollected
                ).toLocaleString("en-IN");


            document.getElementById(
                "monthlyPending"
            ).textContent =
                "₹" +
                Number(
                    summary.totalPending
                ).toLocaleString("en-IN");


            // ================= STUDENTS =================

            document.getElementById(
                "monthlyPaidStudents"
            ).textContent =
                summary.paidStudents;


            document.getElementById(
                "monthlyPartialStudents"
            ).textContent =
                summary.partialStudents;


            document.getElementById(
                "monthlyUnpaidStudents"
            ).textContent =
                summary.unpaidStudents;

        }
        else
        {
            console.error(
                "Monthly summary error:",
                result.message
            );
        }

    }
    catch(error)
    {
        console.error(
            "Monthly payment summary error:",
            error
        );
    }
}


// ================= LOGOUT =================

function logout()
{
    localStorage.removeItem("token");

    window.location.href =
        "login.html";
}


// ================= INITIAL LOAD =================

loadDashboard();
loadMonthlyPaymentSummary();