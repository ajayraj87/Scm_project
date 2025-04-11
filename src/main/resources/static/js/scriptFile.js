// console.log("java script is running");


const toggleSidebar = () => {
	if ($(".sidebar").is(":visible")) {
		// true
		// sidebar band karna h
		$(".sidebar").css("display", "none");
		$(".contect").css("margin-left", "0.5%");
	} else {
		console.log("Sidebar is not visible");
		// false
		// sidebar ko band nhi karna h
		$(".sidebar").css("display", "block");
		$(".contect").css("margin-left", "20%");
	}
};


// This is OnClick function to delete Contact

function deleteContact(cid) {
	swal({
		title: "Are you sure?",
		text: "you want to delete this contact...",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/user/delete_contact/" + cid;
			} else {
				swal("Your contact is safe!");
			}
		});
}

// This is Update Contact function

function updateContact(cid) {
	swal({
		title: "Are you sure to update?",
		text: "you want to update this contact...",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/user/update_contact/" + cid;
			} else {
				swal("Your contact is safe!");
			}
		});
}

// This is Delete User function

function deleteUser() {
	swal({
		title: "Are you sure?",
		text: "you want to delete this contact...",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/user/delete_user";
			} else {
				swal("Your contact is safe!");
			}
		});
}
function updateUser() {
	swal({
		title: "Are you sure?",
		text: "you want to delete this contact...",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/user/update_user";
			} else {
				swal("Your contact is safe!");
			}
		});
}


// This is function for the theme change 

function changeCSS(theme) {
	const linkElement = document.getElementById('theme-stylesheet');

	// Update the CSS file based on the theme selected
	if (theme === 'dark') {
		linkElement.setAttribute('href', '/css/dark-theme.css');
		localStorage.setItem('theme', 'dark'); // Save the theme choice in localStorage
	} else if (theme === 'light') {
		linkElement.setAttribute('href', '/css/light-theme.css');
		localStorage.setItem('theme', 'light'); // Save the theme choice in localStorage
	}
}

// Apply the theme based on saved preference on page load
window.onload = function() {
	const savedTheme = localStorage.getItem('theme'); // Get the saved theme from localStorage
	if (savedTheme) {
		changeCSS(savedTheme); // Apply the saved theme
	} else {
		// If no theme is saved, default to light
		changeCSS('light');
	}
};

// this is function for the combineOTP

function combineOTP() {
	const inputs = document.querySelectorAll(".otp-input");
	let otp = "";
	inputs.forEach(input => otp += input.value);
	document.getElementById("otpValue").value = otp;
	//console.log(otp);
}

