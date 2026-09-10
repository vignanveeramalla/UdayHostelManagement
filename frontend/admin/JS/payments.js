const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}


// ======================================================
// EDIT MODE
// ======================================================

let editingPaymentId = null;

// ======================================================
// MONTHLY PAYMENT TRACKING
// ======================================================

document.addEventListener("DOMContentLoaded", function() {

    const monthInput =
        document.getElementById("monthlyPaymentMonth");

    if (monthInput) {

        const today = new Date();

        const year = today.getFullYear();

        const month = String(
            today.getMonth() + 1
        ).padStart(2, "0");

        monthInput.value =
            `${year}-${month}`;
    }

});

// ======================================================
// LOAD ALL PAYMENTS
// ======================================================

async function loadPayments() {

    try {

        const response = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/payments",
            {
                method: "GET",

                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result = await response.json();

        console.log("Payments response:", result);


        if (response.ok) {

            const payments = result.data;

            const tableBody =
                document.getElementById("paymentTableBody");

            tableBody.innerHTML = "";


            payments.forEach(payment => {

                const row = document.createElement("tr");


                row.innerHTML = `

                    <td>
                        ${payment.paymentId}
                    </td>

                    <td>
                        ${payment.studentId}
                    </td>

                    <td>
                        ₹${payment.amount}
                    </td>

                    <td>
                        ${payment.paymentMethod}
                    </td>

                    <td>
                        ${payment.status}
                    </td>

                    <td>

                        <button
                            onclick="editPayment(${payment.paymentId})">
                            Edit
                        </button>

                        <button
                            onclick="deletePayment(${payment.paymentId})">
                            Delete
                        </button>

                    </td>

                `;


                tableBody.appendChild(row);

            });

        }
        else {

            document.getElementById("message").textContent =
                result.message;

        }

    }
    catch (error) {

        console.error(error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";

    }

}


// ======================================================
// SHOW ADD PAYMENT FORM
// ======================================================

function showAddPaymentForm() {

    editingPaymentId = null;


    document.getElementById("paymentFormContainer")
        .style.display = "block";


    document.getElementById("paymentFormTitle")
        .textContent = "Add Payment";


    document.getElementById("paymentSubmitButton")
        .textContent = "Save Payment";


    document.getElementById("paymentForm")
        .reset();


    document.getElementById("studentId")
        .readOnly = false;

}


// ======================================================
// HIDE PAYMENT FORM
// ======================================================

function hideAddPaymentForm() {

    document.getElementById("paymentFormContainer")
        .style.display = "none";


    document.getElementById("paymentForm")
        .reset();


    editingPaymentId = null;


    document.getElementById("studentId")
        .readOnly = false;


    document.getElementById("paymentFormTitle")
        .textContent = "Add Payment";


    document.getElementById("paymentSubmitButton")
        .textContent = "Save Payment";

}


// ======================================================
// EDIT PAYMENT
// ======================================================

async function editPayment(paymentId) {

    try {

        const response = await fetch(
            `https://udayhostelmanagement-production.up.railway.app/payments/${paymentId}`,
            {
                method: "GET",

                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result = await response.json();

        console.log("Payment details:", result);


        if (response.ok) {

            const payment = result.data;


            // Store payment ID
            editingPaymentId = payment.paymentId;


            // Fill form
            document.getElementById("studentId").value =
             payment.studentId;

            document.getElementById("amount").value =
            payment.amount;

            document.getElementById("paymentDate").value =
            payment.paymentDate;

            document.getElementById("paymentMethod").value =
            payment.paymentMethod;

            document.getElementById("status").value =
            payment.status;


            // Show form
            document.getElementById("paymentFormContainer")
                .style.display = "block";


            // Change heading
            document.getElementById("paymentFormTitle")
                .textContent = "Edit Payment";


            // Change button
            document.getElementById("paymentSubmitButton")
                .textContent = "Update Payment";


            // Don't allow changing student ID
            document.getElementById("studentId")
                .readOnly = true;

        }
        else {

            document.getElementById("message").textContent =
                result.message;

        }

    }
    catch (error) {

        console.error(error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";

    }

}


// ======================================================
// ADD / UPDATE PAYMENT
// ======================================================

document.getElementById("paymentForm").addEventListener(
    "submit",
    async function(event) {

        event.preventDefault();


        const studentId =
            Number(
                document.getElementById("studentId").value
            );


        const amount =
            Number(
                    document.getElementById("amount").value
                );


        const paymentDate =
                 document.getElementById("paymentDate").value;


        const paymentMethod =
                    document.getElementById("paymentMethod").value;

        const status =
            document.getElementById("status").value;


        const paymentData = {

            studentId: studentId,

            amount: amount,

            paymentDate: paymentDate,

            paymentMethod: paymentMethod,

            status: status

        };


        console.log("Payment data:", paymentData);


        let url =
            "https://udayhostelmanagement-production.up.railway.app/payments";

        let method = "POST";


        // ==================================================
        // EDIT MODE
        // ==================================================

        if (editingPaymentId !== null) {

            url =
                `https://udayhostelmanagement-production.up.railway.app/payments/${editingPaymentId}`;

            method = "PUT";

        }


        try {

            const response = await fetch(
                url,
                {
                    method: method,

                    headers: {

                        "Content-Type":
                            "application/json",

                        "Authorization":
                            "Bearer " + token

                    },

                    body:
                        JSON.stringify(paymentData)

                }
            );


            const result =
                await response.json();


            console.log(
                "Save payment response:",
                result
            );


            if (response.ok) {


                if (editingPaymentId !== null) {

                    document.getElementById("message")
                        .textContent =
                        "Payment updated successfully.";

                }
                else {

                    document.getElementById("message")
                        .textContent =
                        "Payment added successfully.";

                }


                hideAddPaymentForm();


                loadPayments();

            }
            else {


                if (result.data) {

                    let errors = "";


                    for (const field in result.data) {

                        errors +=
                            result.data[field] + " ";

                    }


                    document.getElementById("message")
                        .textContent = errors;

                }
                else {

                    document.getElementById("message")
                        .textContent =
                        result.message;

                }

            }

        }
        catch (error) {

            console.error(error);


            document.getElementById("message")
                .textContent =
                "Unable to connect to server.";

        }

    }
);


// ======================================================
// DELETE PAYMENT
// ======================================================

async function deletePayment(paymentId) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete payment ID "
            + paymentId
            + "?"
        );


    if (!confirmDelete) {

        return;

    }


    try {

        const response = await fetch(
            `https://udayhostelmanagement-production.up.railway.app/payments/${paymentId}`,
            {
                method: "DELETE",

                headers: {

                    "Authorization":
                        "Bearer " + token

                }

            }
        );


        const result =
            await response.json();


        console.log(
            "Delete payment response:",
            result
        );


        if (response.ok) {

            document.getElementById("message")
                .textContent =
                "Payment deleted successfully.";


            loadPayments();

        }
        else {

            document.getElementById("message")
                .textContent =
                result.message;

        }

    }
    catch (error) {

        console.error(error);


        document.getElementById("message")
            .textContent =
            "Unable to connect to server.";

    }

}

// ================= STUDENT PAYMENT HISTORY =================

async function searchStudentHistory()
{
    const studentId =
        Number(
            document.getElementById("historyStudentId").value
        );


    if (!studentId)
    {
        document.getElementById("message").textContent =
            "Please enter a valid Student ID.";

        return;
    }


    try
    {
        // Get payment history
        const historyResponse = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/payments/student/" + studentId,
            {
                method: "GET",

                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const historyResult =
            await historyResponse.json();


        console.log(
            "Student payment history:",
            historyResult
        );


        if (!historyResponse.ok)
        {
            document.getElementById("message").textContent =
                historyResult.message;

            document.getElementById("paymentSummary")
                .style.display = "none";

            document.getElementById("historyTableContainer")
                .style.display = "none";

            return;
        }


        const payments = historyResult.data;


        // ================= TABLE =================

        const tableBody =
            document.getElementById("historyTableBody");


        tableBody.innerHTML = "";


        if (payments.length === 0)
        {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="5">
                        No payment history found for Student ID
                        ${studentId}
                    </td>
                </tr>
            `;
        }
        else
        {
            payments.forEach(payment =>
            {
                const row =
                    document.createElement("tr");


                row.innerHTML = `
                    <td>${payment.paymentId}</td>

                    <td>${payment.paymentDate}</td>

                    <td>₹${payment.amount}</td>

                    <td>${payment.paymentMethod}</td>

                    <td>${payment.status}</td>
                `;


                tableBody.appendChild(row);
            });
        }


        document.getElementById("historyTableContainer")
            .style.display = "block";


        // ================= PAYMENT SUMMARY =================

        await loadPaymentSummary(studentId);

    }
    catch(error)
    {
        console.error(error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";
    }
}


async function loadPaymentSummary(studentId)
{
    try
    {
        const response = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/payments/summary/" + studentId,
            {
                method: "GET",

                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result = await response.json();


        console.log(
            "Payment summary:",
            result
        );


        if (!response.ok)
        {
            document.getElementById("paymentSummary")
                .style.display = "none";

            return;
        }


        const summary = result.data;


        document.getElementById("historyTotalFee")
            .textContent =
            "₹" + summary.totalFee;


        document.getElementById("historyTotalPaid")
            .textContent =
            "₹" + summary.totalPaid;


        document.getElementById("historyBalance")
            .textContent =
            "₹" + summary.balance;


        document.getElementById("historyStatus")
            .textContent =
            summary.paymentStatus;


        document.getElementById("paymentSummary")
            .style.display = "grid";

    }
    catch(error)
    {
        console.error(error);
    }
}


// ======================================================
// LOGOUT
// ======================================================

function logout() {

    localStorage.removeItem("token");

    window.location.href =
        "login.html";

}


// ======================================================
// INITIAL LOAD
// ======================================================

loadPayments();

// ======================================================
// LOAD MONTHLY PAYMENT TRACKING
// ======================================================

async function loadMonthlyPayments() {

    const monthValue =
        document.getElementById(
            "monthlyPaymentMonth"
        ).value;

    if (!monthValue) {

        document.getElementById("message")
            .textContent =
            "Please select a month.";

        return;
    }


    const parts = monthValue.split("-");

    const year = Number(parts[0]);

    const month = Number(parts[1]);


    try {

        // ==================================================
        // MONTHLY SUMMARY
        // ==================================================

        const summaryResponse =
            await fetch(
                `https://udayhostelmanagement-production.up.railway.app/payments/monthly-summary?year=${year}&month=${month}`,
                {
                    method: "GET",

                    headers: {
                        "Authorization":
                            "Bearer " + token
                    }
                }
            );


        const summaryResult =
            await summaryResponse.json();


        if (!summaryResponse.ok) {

            document.getElementById("message")
                .textContent =
                summaryResult.message;

            return;
        }


        const summary =
            summaryResult.data;


        // Display summary

        document.getElementById(
            "monthlyTotalStudents"
        ).textContent =
            summary.totalStudents;


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


        console.log("Expected Fee from backend:", summary.totalExpectedFee);

        document.getElementById("monthlyExpectedFee").textContent =
        "₹" + Number(summary.totalExpectedFee).toLocaleString("en-IN");


        document.getElementById("monthlyCollected").textContent =
        "₹" + Number(summary.totalCollected).toLocaleString("en-IN");

        document.getElementById("monthlyPending").textContent =
        "₹" + Number(summary.totalPending).toLocaleString("en-IN");


        document.getElementById(
            "monthlySummary"
        ).style.display = "grid";


        // ==================================================
        // MONTHLY UNPAID / PARTIAL STUDENTS
        // ==================================================

        const unpaidResponse =
            await fetch(
                `https://udayhostelmanagement-production.up.railway.app/payments/monthly-unpaid?year=${year}&month=${month}`,
                {
                    method: "GET",

                    headers: {
                        "Authorization":
                            "Bearer " + token
                    }
                }
            );


        const unpaidResult =
            await unpaidResponse.json();


        if (!unpaidResponse.ok) {

            document.getElementById("message")
                .textContent =
                unpaidResult.message;

            return;
        }


        const unpaidStudents =
            unpaidResult.data;


        const unpaidTableBody =
            document.getElementById(
                "monthlyUnpaidTableBody"
            );


        unpaidTableBody.innerHTML = "";


        if (unpaidStudents.length === 0) {

            unpaidTableBody.innerHTML = `
                <tr>
                    <td colspan="7">
                        All students have paid
                        for this month.
                    </td>
                </tr>
            `;

        }
        else {

            unpaidStudents.forEach(student => {

                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>
                        ${student.studentId}
                    </td>

                    <td>
                        ${student.name}
                    </td>

                    <td>
                        ${student.roomNo}
                    </td>

                    <td>
                        ₹${student.fee}
                    </td>

                    <td>
                        ₹${student.totalPaid}
                    </td>

                    <td>
                        ₹${student.balance}
                    </td>

                    <td>
                        ${student.status}
                    </td>

                `;


                unpaidTableBody
                    .appendChild(row);

            });

        }


        document.getElementById(
            "monthlyUnpaidContainer"
        ).style.display = "block";


        // ==================================================
        // MONTHLY PAYMENT HISTORY
        // ==================================================

        const historyResponse =
            await fetch(
                `https://udayhostelmanagement-production.up.railway.app/payments/monthly-history?year=${year}&month=${month}`,
                {
                    method: "GET",

                    headers: {
                        "Authorization":
                            "Bearer " + token
                    }
                }
            );


        const historyResult =
            await historyResponse.json();


        if (!historyResponse.ok) {

            document.getElementById("message")
                .textContent =
                historyResult.message;

            return;
        }


        const history =
            historyResult.data;


        const historyTableBody =
            document.getElementById(
                "monthlyHistoryTableBody"
            );


        historyTableBody.innerHTML = "";


        if (history.length === 0) {

            historyTableBody.innerHTML = `
                <tr>
                    <td colspan="7">
                        No payments found
                        for this month.
                    </td>
                </tr>
            `;

        }
        else {

            history.forEach(payment => {

                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>
                        ${payment.paymentId}
                    </td>

                    <td>
                        ${payment.studentId}
                    </td>

                    <td>
                        ${payment.studentName}
                    </td>

                    <td>
                        ₹${payment.amount}
                    </td>

                    <td>
                        ${payment.paymentDate}
                    </td>

                    <td>
                        ${payment.paymentMethod}
                    </td>

                    <td>
                        ${payment.status}
                    </td>

                `;


                historyTableBody
                    .appendChild(row);

            });

        }


        document.getElementById(
            "monthlyHistoryContainer"
        ).style.display = "block";


        document.getElementById("message")
            .textContent =
            "Monthly payment details loaded successfully.";

    }
    catch(error) {

        console.error(
            "Monthly payment error:",
            error
        );


        document.getElementById("message")
            .textContent =
            "Unable to connect to server.";

    }

}