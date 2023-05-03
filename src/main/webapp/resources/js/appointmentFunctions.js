function approveAppointment(appointmentId){
    fetch(contextPath + "/appointment/" + appointmentId + "/approve", {method: "POST"})
  	    .then((response) => {
    	    if (response.status == 200 && response.redirected != true){
    		    var element = document.getElementById("appointment-" + appointmentId);
    		    element.parentNode.removeChild(element);
    	    }
 	    });
}

function cancelAppointment(appointmentId){
    fetch(contextPath + "/appointment/" + appointmentId + "/cancel", {method: "POST"})
  	    .then((response) => {
    	    if (response.status == 200 && response.redirected != true){
    		    var element = document.getElementById("appointment-" + appointmentId);
    		    element.parentNode.removeChild(element);
    	    }
 	    });
}