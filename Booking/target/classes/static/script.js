const API = "";


/* ================= LOAD DOCTORS ================= */

async function loadDoctors() {

    const doctorList = document.getElementById("doctorList");
    const doctorSelect = document.getElementById("doctorSelect");

    try {

        const response = await fetch(API + "/doctors");

        if (!response.ok) {
            throw new Error("Unable to load doctors");
        }

        const doctors = await response.json();

        doctorList.innerHTML = "";
        doctorSelect.innerHTML =
            '<option value="">Select a doctor</option>';

        document.getElementById("doctorCount").textContent =
            doctors.length;

        if (doctors.length === 0) {

            doctorList.innerHTML = `
                <div class="empty-state">
                    No doctors available.
                </div>
            `;

            return;
        }

        doctors.forEach(doctor => {

            const card = document.createElement("div");

            card.className = "doctor-card";

            card.innerHTML = `
                <div class="doctor-avatar">👨‍⚕️</div>

                <h3>Dr. ${doctor.name}</h3>

                <div class="specialization">
                    ${doctor.specialization}
                </div>

                <div class="doctor-id">
                    Doctor ID: ${doctor.doctorId}
                </div>
            `;

            doctorList.appendChild(card);


            const option = document.createElement("option");

            option.value = doctor.doctorId;

            option.textContent =
                `Dr. ${doctor.name} - ${doctor.specialization}`;

            doctorSelect.appendChild(option);

        });

    } catch (error) {

        doctorList.innerHTML = `
            <div class="empty-state">
                ❌ Cannot connect to ClinicQ server.
                <br><br>
                Make sure Spring Boot is running.
            </div>
        `;

        console.error(error);
    }
}


/* ================= LOAD DOCTOR SLOTS ================= */

async function loadDoctorSlots() {

    const doctorId =
        document.getElementById("doctorSelect").value;

    const date =
        document.getElementById("appointmentDate").value;

    const slotSelect =
        document.getElementById("slotSelect");


    slotSelect.innerHTML =
        '<option value="">Loading slots...</option>';


    if (!doctorId || !date) {

        slotSelect.innerHTML =
            '<option value="">Select doctor and date</option>';

        return;
    }


    try {

        const response = await fetch(
            `${API}/slots/doctor/${doctorId}/date/${date}`
        );

        if (!response.ok) {
            throw new Error("Unable to load slots");
        }

        const slots = await response.json();

        slotSelect.innerHTML =
            '<option value="">Select an available slot</option>';


        const availableSlots =
            slots.filter(slot => slot.available);


        if (availableSlots.length === 0) {

            slotSelect.innerHTML =
                '<option value="">No slots available</option>';

            return;
        }


        availableSlots.forEach(slot => {

            const option =
                document.createElement("option");

            option.value = slot.slotId;

            option.textContent =
                `${slot.startTime} - ${slot.endTime}`;

            slotSelect.appendChild(option);

        });

    } catch (error) {

        slotSelect.innerHTML =
            '<option value="">Unable to load slots</option>';

        console.error(error);
    }
}


/* ================= BOOK APPOINTMENT ================= */

async function bookAppointment() {

    const patientId =
        document.getElementById("patientId").value;

    const slotId =
        document.getElementById("slotSelect").value;

    const message =
        document.getElementById("bookingMessage");


    if (!patientId) {

        message.textContent =
            "Please enter Patient ID.";

        message.style.color = "#d83232";

        return;
    }


    if (!slotId) {

        message.textContent =
            "Please select an available slot.";

        message.style.color = "#d83232";

        return;
    }


    try {

        const response = await fetch(
            `${API}/appointments/book?slotId=${slotId}&patientId=${patientId}`,
            {
                method: "POST"
            }
        );


        const data = await response.json();


        if (!response.ok) {

            throw new Error(
                data.message || "Booking failed"
            );
        }


        message.textContent =
            "✓ Appointment booked successfully!";

        message.style.color = "#168346";


        loadDoctorSlots();

        loadPatientAppointments();

        updateStatistics();


    } catch (error) {

        message.textContent =
            "❌ " + error.message;

        message.style.color = "#d83232";

        console.error(error);
    }
}


/* ================= LOAD PATIENT APPOINTMENTS ================= */

async function loadPatientAppointments() {

    const patientId =
        document.getElementById("searchPatientId").value;

    const appointmentList =
        document.getElementById("appointmentList");


    if (!patientId) {

        appointmentList.innerHTML = `
            <div class="empty-state">
                Please enter a patient ID.
            </div>
        `;

        return;
    }


    try {

        const response = await fetch(
            `${API}/appointments/patient/${patientId}`
        );


        if (!response.ok) {
            throw new Error("Unable to load appointments");
        }


        const appointments =
            await response.json();


        appointmentList.innerHTML = "";


        if (appointments.length === 0) {

            appointmentList.innerHTML = `
                <div class="empty-state">
                    No appointments found.
                </div>
            `;

            return;
        }


        appointments.forEach(appointment => {

            const card =
                document.createElement("div");

            card.className =
                "appointment-card";


            const slot =
                appointment.slot;


            card.innerHTML = `
                <div>
                    <h3>
                        Dr. ${slot.doctor.name}
                    </h3>

                    <p>
                        ${slot.date}
                        &nbsp; | &nbsp;
                        ${slot.startTime} - ${slot.endTime}
                    </p>

                    <p>
                        Appointment ID:
                        ${appointment.appointmentId}
                    </p>
                </div>

                <div>
                    <span class="status">
                        ${appointment.status}
                    </span>

                    ${
                        appointment.status === "BOOKED"
                        ?
                        `<button
                            class="cancel-button"
                            onclick="cancelAppointment(${appointment.appointmentId})">
                            Cancel
                        </button>`
                        :
                        ""
                    }
                </div>
            `;


            appointmentList.appendChild(card);

        });

    } catch (error) {

        appointmentList.innerHTML = `
            <div class="empty-state">
                ❌ Unable to load appointments.
            </div>
        `;

        console.error(error);
    }
}


/* ================= CANCEL APPOINTMENT ================= */

async function cancelAppointment(appointmentId) {

    if (!confirm("Are you sure you want to cancel this appointment?")) {
        return;
    }


    try {

        const response = await fetch(
            `${API}/appointments/${appointmentId}/cancel`,
            {
                method: "PUT"
            }
        );


        if (!response.ok) {
            throw new Error("Cancellation failed");
        }


        alert("Appointment cancelled successfully.");

        loadPatientAppointments();

        loadDoctors();

        updateStatistics();

    } catch (error) {

        alert("Unable to cancel appointment.");

        console.error(error);
    }
}


/* ================= STATISTICS ================= */

async function updateStatistics() {

    try {

        const doctorsResponse =
            await fetch(API + "/doctors");

        const doctors =
            await doctorsResponse.json();

        document.getElementById("doctorCount").textContent =
            doctors.length;


        const patientsResponse =
            await fetch(API + "/patients");

        const patients =
            await patientsResponse.json();

        document.getElementById("patientCount").textContent =
            patients.length;

    } catch (error) {

        console.error(error);
    }
}


/* ================= SECTION NAVIGATION ================= */

function showSection(section) {

    if (section === "appointment") {

        document.getElementById("appointment")
            .scrollIntoView({
                behavior: "smooth"
            });

    }

    if (section === "doctors") {

        document.getElementById("doctors")
            .scrollIntoView({
                behavior: "smooth"
            });

    }
}


/* ================= PAGE START ================= */

document.addEventListener("DOMContentLoaded", function () {

    loadDoctors();

    updateStatistics();

});