const API_URL = "http://localhost:8080";

async function loadFacilities()
{
    const facilityGrid =
        document.getElementById("facilityGrid");

    if (!facilityGrid)
    {
        console.error("facilityGrid not found.");
        return;
    }

    try
    {
        facilityGrid.innerHTML = `
            <div class="facility-loading">
                Loading facilities...
            </div>
        `;

        const response = await fetch(
            API_URL + "/facilities",
            {
                method: "GET"
            }
        );

        const result = await response.json();

        console.log("Facilities response:", result);

        if (!response.ok)
        {
            facilityGrid.innerHTML = `
                <div class="facility-error">
                    Unable to load facilities.
                    <br>
                    Please try again later.
                </div>
            `;

            console.error(
                "Facilities API error:",
                result
            );

            return;
        }


        const facilities = result.data || [];

        facilityGrid.innerHTML = "";


        if (facilities.length === 0)
        {
            facilityGrid.innerHTML = `
                <div class="facility-error">
                    No facilities available.
                </div>
            `;

            return;
        }


        facilities.forEach(facility =>
        {
            const name =
                (facility.name || "")
                .toUpperCase();

            let displayName = facility.name;
            let icon = "⭐";

            if (name === "WIFI")
            {
                displayName = "Free Wi-Fi";
                icon = "📶";
            }
            else if (name === "LAUNDRY")
            {
                displayName = "Washing Machine";
                icon = "🧺";
            }
            else if (name === "MESS")
            {
                displayName = "Homely Food";
                icon = "🍛";
            }
            else if (name === "CCTV")
            {
                displayName = "24/7 CCTV Surveillance";
                icon = "📹";
            }
            else if (name === "MINERAL WATER")
            {
                displayName = "Mineral Water";
                icon = "💧";
            }
            else if (name === "DAILY ROOM CLEANING")
            {
                displayName = "Daily Room Cleaning";
                icon = "🧹";
            }


            const card =
                document.createElement("div");

            card.className = "facility-card";


            card.innerHTML = `

                <div class="facility-icon">

                    ${icon}

                </div>


                <h3>

                    ${displayName}

                </h3>


                <p>

                    ${facility.description || "Facility available for residents."}

                </p>


                <span class="facility-status">

                    ${
                        facility.available === false
                        ? "Currently Unavailable"
                        : "Available"
                    }

                </span>

            `;


            facilityGrid.appendChild(card);

        });

    }
    catch(error)
    {
        console.error(
            "Facilities loading error:",
            error
        );

        facilityGrid.innerHTML = `
            <div class="facility-error">

                Unable to connect to hostel server.

                <br><br>

                Please make sure Spring Boot
                application is running.

            </div>
        `;
    }
}


loadFacilities();