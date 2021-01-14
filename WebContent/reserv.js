function getReservations() {
		  var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		      document.getElementById("demo").innerHTML = this.responseText;
		    }
		  };
		  xhttp.open("GET", "Reservations", true);
		  xhttp.send();
		}
function deleteReservation(){
	console.log("hello");
	let btn = event.target;
	let id = btn.id;
	console.log(id);
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = reloadReq;
	xhttp.open('POST', 'ReservationsDelete');
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("id="+id);
	function reloadReq(){
		if(xhttp.readyState === 4 && xhttp.status === 200) {
			location.reload();
		}
	}
}
//the form stuff
var span = document.getElementsByClassName("close")[0];
var modal = document.getElementById("myModal");


function updateForm(){
	let btn = event.target;
	let id = btn.id;
	var modal = document.getElementById("myModal");
	var reservId = document.getElementById("reserv");
	var t = document.createTextNode(id);
	reservId.appendChild(t);
	 modal.style.display = "block";
	 
}

function closeModal(){
	var modal = document.getElementById("myModal");
	 modal.style.display = "none";
}

