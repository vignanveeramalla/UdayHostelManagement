// ======================================================
// UDAY BOYS HOSTEL
// PUBLIC ROOMS PAGE
// ======================================================

window.addEventListener("load", function () {

    loadRooms();

});


// ======================================================
// LOAD ROOMS FROM SPRING BOOT
// ======================================================

async function loadRooms() {

    const roomGrid =
        document.getElementById("roomGrid");


    // Check whether HTML element exists
    if (!roomGrid) {

        console.error(
            "roomGrid element was not found."
        );

        return;
    }


    // Show loading message
    roomGrid.innerHTML = `

        <div class="rooms-loading">

            Loading rooms...

        </div>

    `;


    try {

        console.log(
            "Connecting to rooms API..."
        );


        const response = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/rooms",
            {
                method: "GET",

                headers: {
                    "Accept": "application/json"
                }
            }
        );


        console.log(
            "Rooms API status:",
            response.status
        );


        const result =
            await response.json();


        console.log(
            "Rooms API response:",
            result
        );


        // Check HTTP response
        if (!response.ok) {

            throw new Error(
                result.message ||
                "Unable to fetch rooms"
            );

        }


        // Backend response:
        // result.data = room list

        const rooms = result.data;


        if (
            !rooms ||
            !Array.isArray(rooms)
        ) {

            throw new Error(
                "Invalid room data received from server."
            );

        }


        if (rooms.length === 0) {

            roomGrid.innerHTML = `

                <div class="rooms-loading">

                    No rooms available.

                </div>

            `;

            return;
        }


        // Remove loading message
        roomGrid.innerHTML = "";


        // Sort room numbers
        rooms.sort(function (a, b) {

            return Number(a.roomNo)
                 - Number(b.roomNo);

        });


        // ==================================================
        // CREATE ROOM CARDS
        // ==================================================

        rooms.forEach(function (room) {


            const card =
                document.createElement("div");


            card.className =
                "public-room-card";


            card.innerHTML = `

                <div class="room-top">

                    <div class="room-number">

                        ${room.roomNo}

                    </div>

                    <div class="room-label">

                        Uday Boys Hostel

                    </div>

                </div>


                <div class="room-details">


                    <div class="room-detail">

                        <strong>
                            Floor
                        </strong>

                        <span>
                            ${room.floor}
                        </span>

                    </div>


                    <div class="room-detail">

                        <strong>
                            Room Type
                        </strong>

                        <span>
                            Comfortable Room
                        </span>

                    </div>


                    <div class="room-detail">

                        <strong>
                            Accommodation
                        </strong>

                        <span>
                            Student Friendly
                        </span>

                    </div>


                    <a
                        href="enquiry.html"
                        class="room-action room-enquiry-button"
                    >

                        ✉ Send Enquiry

                    </a>


                </div>

            `;


            roomGrid.appendChild(card);

        });


        console.log(
            rooms.length +
            " rooms displayed successfully."
        );

    }


    catch (error) {

        console.error(
            "Rooms loading error:",
            error
        );


        roomGrid.innerHTML = `

            <div class="rooms-error">

                Unable to load room information.
                <br><br>

                Please contact Uday Boys Hostel
                for room details.

            </div>

        `;

    }

}