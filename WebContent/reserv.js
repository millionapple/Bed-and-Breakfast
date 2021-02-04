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
	var info = document.getElementById(id).childNodes;
	var modal = document.getElementById("myModal");
	console.log(info);
	document.getElementById("resId").setAttribute("value", info[0].innerText);
	document.getElementById("name").setAttribute("value", info[1].innerText);
	document.getElementById("email").setAttribute("value", info[2].innerText);
	document.getElementById("phone").setAttribute("value", info[3].innerText);
	document.getElementById("arrival").setAttribute("value", info[5].innerText);
	document.getElementById("departure").setAttribute("value", info[6].innerText);
//	document.getElementById("rooms").setAttribute("value", info[7].innerText);
	 modal.style.display = "block";
	 
}

function closeModal(){
	var modal = document.getElementById("myModal");
	 modal.style.display = "none";
}

function minArrival(){
	arrival.min = new Date().toISOString().split("T")[0];
}
function minDeparture(){
	departure.min = arrival.value;
	validRooms();
}
function validRooms(){
	var arrival = document.getElementById("arrival");
	var departure = document.getElementById("departure");
	console.log(arrival.value);
	console.log(departure.value);
	if(arrival.value && departure.value){
		console.log("Getting all Reservations");
		    let xhttp = new XMLHttpRequest();
		    xhttp.onreadystatechange = parseReservations;
		    xhttp.open('GET', 'Reservations');
		    xhttp.send();
		    
		    function parseReservations(){
				 if (xhttp.readyState === 4 && xhttp.status === 200) {
			         let reqs = this.responseText;
//			         reqs = JSON.parse(reqs);
			         console.log(reqs);
				 }
			}
		    
		var i = 1;
		document.getElementById("room"+i).disabled = true;
	};
}

