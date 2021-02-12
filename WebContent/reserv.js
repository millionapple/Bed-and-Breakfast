function getReservations() {
		  var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	let reqs = this.responseText;
		         reqs = JSON.parse(reqs);
		         var str = "";
			     var res = reqs.reservation;
		         for(i in res){
		        	 str += "<tr id='"+res[i].reservId+"'><td>"+res[i].reservId+"</td><td>"+res[i].guestName+"</td><td>"+res[i].email+"</td><td>"+res[i].phone+"</td>" +
		        	 		"<td>"+res[i].arrival+"</td><td>"+res[i].departure+"</td><td>"+res[i].rooms+"</td><td>"+res[i].days+"</td><td>"+res[i].price+"</td>" +
		        	 		"<td><button onclick=\"deleteReservation()\" id=\""+res[i].reservId+"\">Delete</button></td>" +
		        	 		"<td><input type=\"button\" onClick=\"updateForm()\" value=\"Update\" id=\""+res[i].reservId+"\"/></td></tr>"
		         }
		         document.getElementById("demo").innerHTML = "<table>" +
		      		"<tr><th>Reservation id</th><th>Guest Name</th><th>Email</th><th>Phone</th><th>Arrival</th><th>Departure</th>" +
		      		"<th>Rooms</th><th>Days</th><th>Price</th><th>Delete</th><th>Update</th></tr>" +
		      		str+
		      		"</table>";
		      console.log(reqs);
		    }
		  };
		  xhttp.open("GET", "Reservations", true);
		  xhttp.send();
		}
function deleteReservation(){
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
			         reqs = JSON.parse(reqs);
			         console.log(reqs);
				 }
			}
		    
		var i = 1;
		document.getElementById("room"+i).disabled = true;
	};
}

