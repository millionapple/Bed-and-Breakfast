today = new Date();
currentMonth = today.getMonth();
currentYear = today.getFullYear();
selectYear = document.getElementById("year");
selectMonth = document.getElementById("month");

months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

monthAndYear = document.getElementById("monthAndYear");
showCalendar(currentMonth, currentYear);


function next() {
    currentYear = (currentMonth === 11) ? currentYear + 1 : currentYear;
    currentMonth = (currentMonth + 1) % 12;
    showCalendar(currentMonth, currentYear);
}

function previous() {
    currentYear = (currentMonth === 0) ? currentYear - 1 : currentYear;
    currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
    showCalendar(currentMonth, currentYear);
}

function jump() {
    currentYear = parseInt(selectYear.value);
    currentMonth = parseInt(selectMonth.value);
    showCalendar(currentMonth, currentYear);
}

function showCalendar(month, year) {
    let firstDay = (new Date(year, month)).getDay();

    tbl = document.getElementById("calendar-body"); // body of the calendar

    // clearing all previous cells
    tbl.innerHTML = "";

    // filing data about month and in the page via DOM.
    monthAndYear.innerHTML = months[month] + " " + year;
    selectYear.value = year;
    selectMonth.value = month;
    // creating all cells
    let date = 1;
    let day = 0;
    for (let i = 0; i < 6; i++) {
        // creates a table row
        let row = document.createElement("tr");
        row.id = "row-"+i;

        //creating individual cells, filing them up with data.
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay) {
                cell = document.createElement("td");
                cellText = document.createTextNode("");
                cell.appendChild(cellText);
                row.appendChild(cell);
            }
            else if (date > daysInMonth(month, year)) {
                break;
            }

            else {
            	day +=1;
                cell = document.createElement("td");
                cell.id = "cell-"+day;
                cellText = document.createTextNode(date);
                if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
                    cell.classList.add("bg-info");
                } // color today's date
                //create room list
                list = document.createElement("ul");
                list.className = "grid-container";
                for(let l = 1; l<=8; l++){
                	listElement = document.createElement("LI");
                	listElement.className = "grid-item";
                	listElement.id = "grid-"+l;
                	room = document.createTextNode(l);
                	listElement.appendChild(room);
                	list.appendChild(listElement);
                }
                cell.appendChild(cellText);
                cell.appendChild(list);
                row.appendChild(cell);
                date++;
            }


        }
        tbl.appendChild(row); // appending each row into calendar body.
    }
    getReservations();
}


// check how many days in a month code from https://dzone.com/articles/determining-number-days-month
function daysInMonth(iMonth, iYear) {
    return 32 - new Date(iYear, iMonth, 32).getDate();
}

function getReservations(){
	let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = parseReservations;
    xhttp.open('GET', 'Reservations');
    xhttp.send();
    function parseReservations(){
		 if (xhttp.readyState === 4 && xhttp.status === 200) {
	         let reqs = this.responseText;
	         reqs = JSON.parse(reqs);
	         var res = reqs.reservation;
	         for(i in res){
	         arrival = getday(res[i].arrival);
	         aMonth = getMonth(res[i].arrival);
	         departure = getday(res[i].departure);
	         dMonth = getMonth(res[i].departure);
		         if(parseInt(selectMonth.value)+1 == aMonth){
				         for(l = arrival; l <= departure; l++){
				        	 var day = document.getElementById("cell-"+l);
					         for(j in res[i].roomIds){
					        	 if(res[i].roomIds.children === null){}
					        	 else{day.children[0].children[res[i].roomIds[j]-1].style.backgroundColor = "red";}
					         }
				         }
		         } 
		         //if the reservation goes into the next month
		         if(parseInt(selectMonth.value)+1 == dMonth && parseInt(selectMonth.value)+1 != aMonth){
	        		 for(l = 1; l <= departure; l++){
	        			 var day = document.getElementById("cell-"+l);
	        			 for(j in res[i].roomIds){
	        				 if(res[i].roomIds.children === null){}
	        				 else{day.children[0].children[res[i].roomIds[j]-1].style.backgroundColor = "red";
	        				 	console.log("cell-"+l);
	        				 }
	        			 }
	        		 }
	        	 }
	         }
		 }
    }
}

function getday(date){
	day = date.substr(8);
	if(day.charAt(0) ==='0'){
		day = day.replace("0", "");
	}
	return day;
}

function getMonth(date){
	month = date.substring(5, 7);
	if(month.charAt(0) === '0' ){
		month = month.replace("0", "");
	}
	return month;
}
