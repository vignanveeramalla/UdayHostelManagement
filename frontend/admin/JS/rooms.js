const token = localStorage.getItem("token");


if (!token) {

    window.location.href = "login.html";

}


// =====================================================
// EDITING ROOM
// null = ADD
// number = EDIT
// =====================================================

let editingRoomNo = null;



// =====================================================
// LOAD ALL ROOMS
// =====================================================

async function loadRooms() {

    try {

        const response = await fetch(
            "http://localhost:8080/rooms",
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
            "Rooms response:",
            result
        );


        if (response.ok) {

            const rooms = result.data;

            const tableBody =
                document.getElementById(
                    "roomTableBody"
                );


            tableBody.innerHTML = "";


            // Get availability for every room

            for (const room of rooms) {

                const availability =
                    await getRoomAvailability(
                        room.roomNo
                    );


                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>
                        ${room.roomNo}
                    </td>


                    <td>
                        ${room.capacity}
                    </td>


                    <td>
                        ${room.floor}
                    </td>


                    <td>
                        ${availability.occupied}
                    </td>


                    <td>
                        ${availability.available}
                    </td>


                    <td>
                        ${
                            availability.full
                            ? "FULL"
                            : "AVAILABLE"
                        }
                    </td>


                    <td>

                        <button
                            type="button"
                            onclick="editRoom(${room.roomNo})">

                            Edit

                        </button>


                        <button
                            type="button"
                            onclick="deleteRoom(${room.roomNo})">

                            Delete

                        </button>

                    </td>

                `;


                tableBody.appendChild(row);

            }

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
// GET ROOM AVAILABILITY
// =====================================================

async function getRoomAvailability(roomNo) {

    try {

        const response = await fetch(
            "http://localhost:8080/rooms/"
            + roomNo
            + "/availability",
            {
                method: "GET",

                headers: {
                    "Authorization":
                        "Bearer " + token
                }
            }
        );


        const result =
            await response.json();


        if (response.ok) {

            return result.data;

        }


        return {

            occupied: 0,

            available: 0,

            full: false

        };

    }
    catch (error) {

        console.error(error);


        return {

            occupied: 0,

            available: 0,

            full: false

        };

    }

}



// =====================================================
// SHOW ADD ROOM FORM
// =====================================================

function showAddRoomForm() {

    editingRoomNo = null;


    document.getElementById(
        "roomFormTitle"
    ).textContent =
        "Add Room";


    document.getElementById(
        "roomSubmitButton"
    ).textContent =
        "Save Room";


    document.getElementById(
        "roomForm"
    ).reset();


    // Room number can be entered while adding

    document.getElementById(
        "roomNo"
    ).disabled = false;


    document.getElementById(
        "roomFormContainer"
    ).style.display =
        "block";


    document.getElementById(
        "roomFormContainer"
    ).scrollIntoView({
        behavior: "smooth"
    });

}



// =====================================================
// HIDE ROOM FORM
// =====================================================

function hideRoomForm() {

    document.getElementById(
        "roomFormContainer"
    ).style.display =
        "none";


    document.getElementById(
        "roomForm"
    ).reset();


    editingRoomNo = null;


    document.getElementById(
        "roomNo"
    ).disabled = false;


    document.getElementById(
        "roomFormTitle"
    ).textContent =
        "Add Room";


    document.getElementById(
        "roomSubmitButton"
    ).textContent =
        "Save Room";

}



// =====================================================
// EDIT ROOM
// =====================================================

async function editRoom(roomNo) {

    console.log(
        "EDIT ROOM:",
        roomNo
    );


    try {

        const response = await fetch(
            "http://localhost:8080/rooms/"
            + roomNo,
            {
                method: "GET",

                headers: {
                    "Authorization":
                        "Bearer " + token
                }
            }
        );


        const result =
            await response.json();


        console.log(
            "Room details:",
            result
        );


        if (!response.ok) {

            document.getElementById(
                "message"
            ).textContent =
                result.message;

            return;

        }


        const room = result.data;


        // Store room number

        editingRoomNo = roomNo;


        // Change form title

        document.getElementById(
            "roomFormTitle"
        ).textContent =
            "Edit Room";


        document.getElementById(
            "roomSubmitButton"
        ).textContent =
            "Update Room";


        // Fill form

        document.getElementById(
            "roomNo"
        ).value =
            room.roomNo;


        document.getElementById(
            "capacity"
        ).value =
            room.capacity;


        document.getElementById(
            "floor"
        ).value =
            room.floor;


        // Do not allow changing room number

        document.getElementById(
            "roomNo"
        ).disabled = true;


        document.getElementById(
            "roomFormContainer"
        ).style.display =
            "block";


        document.getElementById(
            "roomFormContainer"
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
// ADD / UPDATE ROOM
// =====================================================

document.getElementById(
    "roomForm"
).addEventListener(
    "submit",
    async function(event) {

        event.preventDefault();


        const roomNo =
            Number(
                document.getElementById(
                    "roomNo"
                ).value
            );


        const capacity =
            Number(
                document.getElementById(
                    "capacity"
                ).value
            );


        const floor =
            Number(
                document.getElementById(
                    "floor"
                ).value
            );


        const roomData = {

            roomNo: roomNo,

            capacity: capacity,

            floor: floor

        };


        let url;

        let method;


        // =================================================
        // ADD
        // =================================================

        if (editingRoomNo === null) {

            console.log(
                "ROOM MODE = ADD"
            );


            url =
                "http://localhost:8080/rooms";


            method = "POST";

        }


        // =================================================
        // UPDATE
        // =================================================

        else {

            console.log(
                "ROOM MODE = UPDATE"
            );


            url =
                "http://localhost:8080/rooms/"
                + editingRoomNo;


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
                                roomData
                            )

                    }
                );


            const result =
                await response.json();


            console.log(
                "Room save/update response:",
                result
            );


            if (response.ok) {

                if (method === "POST") {

                    document.getElementById(
                        "message"
                    ).textContent =
                        "Room added successfully.";

                }
                else {

                    document.getElementById(
                        "message"
                    ).textContent =
                        "Room updated successfully.";

                }


                hideRoomForm();


                loadRooms();

            }
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
// DELETE ROOM
// =====================================================

async function deleteRoom(roomNo) {

    console.log(
        "DELETE ROOM:",
        roomNo
    );


    const confirmDelete =
        confirm(
            "Are you sure you want to delete room "
            + roomNo
            + "?"
        );


    if (!confirmDelete) {

        return;

    }


    try {

        const response =
            await fetch(
                "http://localhost:8080/rooms/"
                + roomNo,
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
            "Delete room response:",
            result
        );


        if (response.ok) {

            document.getElementById(
                "message"
            ).textContent =
                "Room deleted successfully.";


            loadRooms();

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
// LOGOUT
// =====================================================

function logout() {

    localStorage.removeItem("token");

    window.location.href =
        "login.html";

}



// =====================================================
// INITIAL LOAD
// =====================================================

loadRooms();