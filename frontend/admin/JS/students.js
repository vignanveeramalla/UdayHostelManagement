const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}


// =====================================================
// EDITING STUDENT ID
// null  = Add mode
// number = Edit mode
// =====================================================

let editingStudentId = null;


// =====================================================
// LOAD ALL STUDENTS
// =====================================================

async function loadStudents() {

    try {

        const response = await fetch(
            "http://localhost:8080/students",
            {
                method: "GET",

                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result = await response.json();

        console.log("Students response:", result);


        if (response.ok) {

            const students = result.data;

            const tableBody =
                document.getElementById("studentTableBody");


            tableBody.innerHTML = "";


            students.forEach(student => {

                const row = document.createElement("tr");


                row.innerHTML = `

                    <td>${student.studentId}</td>

                    <td>${student.name}</td>

                    <td>${student.roomNo}</td>

                    <td>${student.college}</td>

                    <td>₹${student.fee}</td>

                    <td>

                        <button
                            type="button"
                            onclick="editStudent(${student.studentId})">

                            Edit

                        </button>


                        <button
                            type="button"
                            onclick="deleteStudent(${student.studentId})">

                            Delete

                        </button>

                    </td>

                `;


                tableBody.appendChild(row);

            });

        }
        else {

            document.getElementById(
                "message"
            ).textContent = result.message;

        }

    }
    catch (error) {

        console.error(error);

        document.getElementById(
            "message"
        ).textContent =
            "Unable to connect to server.";

    }

}


// =====================================================
// LOGOUT
// =====================================================

function logout() {

    localStorage.removeItem("token");

    window.location.href = "login.html";

}


// =====================================================
// SHOW ADD STUDENT FORM
// =====================================================

function showAddStudentForm() {

    // IMPORTANT:
    // null means ADD mode

    editingStudentId = null;


    document.getElementById(
        "studentFormTitle"
    ).textContent = "Add Student";


    document.getElementById(
        "studentSubmitButton"
    ).textContent = "Save Student";


    document.getElementById(
        "studentForm"
    ).reset();


    document.getElementById(
        "studentFormContainer"
    ).style.display = "block";


    document.getElementById(
        "studentFormContainer"
    ).scrollIntoView({
        behavior: "smooth"
    });

}


// =====================================================
// HIDE FORM
// =====================================================

function hideAddStudentForm() {

    document.getElementById(
        "studentFormContainer"
    ).style.display = "none";


    document.getElementById(
        "studentForm"
    ).reset();


    editingStudentId = null;


    document.getElementById(
        "studentFormTitle"
    ).textContent = "Add Student";


    document.getElementById(
        "studentSubmitButton"
    ).textContent = "Save Student";

}


// =====================================================
// EDIT STUDENT
// =====================================================

async function editStudent(studentId) {

    console.log(
        "EDIT BUTTON CLICKED. Student ID:",
        studentId
    );


    try {

        const response = await fetch(
            "http://localhost:8080/students/" + studentId,
            {
                method: "GET",

                headers: {
                    "Authorization":
                        "Bearer " + token
                }
            }
        );


        const result = await response.json();


        console.log(
            "Student details:",
            result
        );


        if (!response.ok) {

            document.getElementById(
                "message"
            ).textContent =
                result.message;

            return;
        }


        const student = result.data;


        // =================================================
        // VERY IMPORTANT
        // STORE THE ID WE ARE EDITING
        // =================================================

        editingStudentId = studentId;


        console.log(
            "editingStudentId =",
            editingStudentId
        );


        // =================================================
        // CHANGE FORM TO EDIT MODE
        // =================================================

        document.getElementById(
            "studentFormTitle"
        ).textContent =
            "Edit Student";


        document.getElementById(
            "studentSubmitButton"
        ).textContent =
            "Update Student";


        // =================================================
        // FILL EXISTING STUDENT DATA
        // =================================================

        document.getElementById(
            "studentName"
        ).value =
            student.name || "";


        document.getElementById(
            "roomNo"
        ).value =
            student.roomNo || "";


        document.getElementById(
            "college"
        ).value =
            student.college || "";


        document.getElementById(
            "fee"
        ).value =
            student.fee || "";


        document.getElementById(
            "address"
        ).value =
            student.address || "";


        document.getElementById(
            "mobile"
        ).value =
            student.mobile || "";


        document.getElementById(
            "joiningDate"
        ).value =
            student.joiningDate || "";


        // =================================================
        // SHOW FORM
        // =================================================

        document.getElementById(
            "studentFormContainer"
        ).style.display = "block";


        document.getElementById(
            "studentFormContainer"
        ).scrollIntoView({
            behavior: "smooth"
        });

    }
    catch (error) {

        console.error(error);

        document.getElementById(
            "message"
        ).textContent =
            "Unable to connect to server.";

    }
}

// =====================================================
// DELETE STUDENT
// =====================================================

async function deleteStudent(studentId) {

    console.log("DELETE BUTTON CLICKED. Student ID:", studentId);


    // Ask for confirmation

    const confirmDelete = confirm(
        "Are you sure you want to delete student ID " + studentId + "?"
    );


    if (!confirmDelete) {
        return;
    }


    try {

        const response = await fetch(
            "http://localhost:8080/students/" + studentId,
            {
                method: "DELETE",

                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result = await response.json();


        console.log(
            "Delete student response:",
            result
        );


        if (response.ok) {

            document.getElementById(
                "message"
            ).textContent =
                "Student deleted successfully.";


            // Reload student list

            loadStudents();

        }
        else {

            document.getElementById(
                "message"
            ).textContent =
                result.message;

        }

    }
    catch (error) {

        console.error(error);


        document.getElementById(
            "message"
        ).textContent =
            "Unable to connect to server.";

    }

}

// =====================================================
// FORM SUBMIT
// =====================================================

document.getElementById(
    "studentForm"
).addEventListener(
    "submit",
    async function(event) {

        event.preventDefault();


        // =================================================
        // GET FORM VALUES
        // =================================================

        const name =
            document.getElementById(
                "studentName"
            ).value.trim();


        const roomNo =
            Number(
                document.getElementById(
                    "roomNo"
                ).value
            );


        const college =
            document.getElementById(
                "college"
            ).value.trim();


        const fee =
            Number(
                document.getElementById(
                    "fee"
                ).value
            );


        const address =
            document.getElementById(
                "address"
            ).value.trim();


        const mobile =
            document.getElementById(
                "mobile"
            ).value.trim();


        const joiningDate =
            document.getElementById(
                "joiningDate"
            ).value;


        // =================================================
        // CREATE STUDENT OBJECT
        // =================================================

        const studentData = {

            name: name,

            roomNo: roomNo,

            college: college,

            fee: fee,

            address: address,

            mobile: mobile,

            joiningDate: joiningDate

        };


        console.log(
            "Student data:",
            studentData
        );


        // =================================================
        // DECIDE ADD OR UPDATE
        // =================================================

        let url;

        let method;


        if (editingStudentId === null) {

            // =============================================
            // ADD STUDENT
            // =============================================

            console.log(
                "MODE = ADD"
            );


            url =
                "http://localhost:8080/students";


            method = "POST";

        }
        else {

            // =============================================
            // UPDATE STUDENT
            // =============================================

            console.log(
                "MODE = UPDATE"
            );


            console.log(
                "Updating student ID:",
                editingStudentId
            );


            url =
                "http://localhost:8080/students/"
                + editingStudentId;


            method = "PUT";

        }


        console.log(
            "HTTP Method:",
            method
        );


        console.log(
            "URL:",
            url
        );


        try {

            const response =
                await fetch(
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
                            JSON.stringify(
                                studentData
                            )
                    }
                );


            const result =
                await response.json();


            console.log(
                "Save/Update response:",
                result
            );


            // =================================================
            // SUCCESS
            // =================================================

            if (response.ok) {

                if (method === "POST") {

                    document.getElementById(
                        "message"
                    ).textContent =
                        "Student added successfully.";

                }
                else {

                    document.getElementById(
                        "message"
                    ).textContent =
                        "Student updated successfully.";

                }


                hideAddStudentForm();


                // Reload table

                loadStudents();

            }


            // =================================================
            // ERROR
            // =================================================

            else {

                if (result.data) {

                    let errors = "";


                    for (
                        const field in result.data
                    ) {

                        errors +=
                            result.data[field]
                            + " ";

                    }


                    document.getElementById(
                        "message"
                    ).textContent =
                        errors;

                }
                else {

                    document.getElementById(
                        "message"
                    ).textContent =
                        result.message;

                }

            }

        }
        catch (error) {

            console.error(error);

            document.getElementById(
                "message"
            ).textContent =
                "Unable to connect to server.";

        }

    }
);


// =====================================================
// INITIAL LOAD
// =====================================================

loadStudents();