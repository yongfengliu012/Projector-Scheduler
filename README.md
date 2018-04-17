# Projector-Scheduler

  1.	Project Objectives
To provide online scheduling services to employees so that they could place reservations for 10 office projectors with given time. Assumption: all projectors are physically working once added to the system and are available from 8am to 5pm daily. 

  2.	Services Provided
	Get all existing projectors 
	Get one projector by id	Add projectors to the projector list
	Delete projector in the case of maintenance 
	Get all the reservations 
	Reserve a projector on a given day from time X to Y
	Cancel an existing reservation 
	Check the status of a reservation with the reservation id 
	Modify an existing reservation to another time

  3.	Demo of Testing the Web Services

  1)	Get All the Projectors (Note: if you see only one projector returned with this service, it’s because of that only one projector named P1 has been initialized by the system itself. You can add 9 more projectors using the ADD projector service bellow, so that you could have total of 10 projectors working. ) 
•	URL
http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors
•	Method:
<GET>
•	URL Params
<NONE>
•	Success Response:
<Code with 200 and projectors id & name in json format>
 •	Error Response:
<Wrong URLs result in response of status code 404>

   2)	Get a Projector by projector id
•	URL
http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors/{id}
e.g. http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors/1
•	Method:
<GET>
•	URL Params
<id:  integer>
•	Success Response:
<Code with 200 and the requested projector id & name in json format>
•	Error Response:
<Wrong URLs result in response of status code 404>

  3)	Add One Projector
•	URL
http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors/{id}/{name}
e.g. http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors/11/P11
•	Method:
<POST>
•	URL Params
<id: integer; name: String>
•	Success Response:
<Code with 200 and return result of “success”> 
•	Error Response:
<Wrong URLs result in response of status code 404; trying to add existing projector one more time results in return code of 200 and result of “failure”>
 
   4)	Delete One Projector
•	URL
http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors/{id}
e.g. http://localhost:8080/ProjectorScheduler/rest/ProjectorService/projectors/12
•	Method:
<DELETE>
•	URL Params
<id: integer>
•	Success Response:
<Code with 200 and return result of “success”> 
•	Error Response:
<Wrong URLs result in response of status code 404; trying to delete NOT existing projector results in return code of 200 and result of “failure”>
	
 
  5)	Get All Reservations
•	URL
< http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations>
•	Method:
<GET>
•	URL Params
NONE
•	Success Response:
<Code with 200 and all the scheduled reservations in json format> 
•	Error Response:
<Wrong URLs result in response of status code 404>

  6)	Add One Reservation
•	URL
http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/{date}/{startTime}/{endTime}
e.g.  http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/20180409/1130/1200
•	Method:
<POST>
•	URL Params
<date: strings contain non-digit symbols; startTime: strings contain non-digit symbols; endTime: strings contain non-digit symbols>
•	Success Response:
<Code with 200 and return result with details of the successfully created reservation: reservationID, projectorName, startTime, endTime> 
•	Error Response:
<Wrong URLs result in response of status code 404; trying to add reservation conflicting with other existing reservations result in time results in return code of 200 and result of “No projector available with given time”>

 
  7)	Get a Reservation by reservation id
•	URL http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/{reservationId}
e.g. http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/1001
•	Method:
<GET>
•	URL Params
<reservationId:  integer>
•	Success Response:
<Code with 200 and the requested details of the reservation with: reservationId, projectorName, startTime, endTime> 
•	Error Response:
<Wrong URLs result in response of status code 404; requesting non-existing reservation results in status code of 200 and return result of “No such reservation with input id”>

 
  8)	Delete One Existing Reservation
•	URL
http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/{reservationId}
e.g. http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/1002
•	Method:
<DELETE>
•	URL Params
<reservationId: integer>
•	Success Response:
<Code with 200 and return result of “delete is success for reservation with id: the given id”> 
•	Error Response:
<Wrong URLs result in response of status code 404; trying to delete NOT existing reservation results in return code of 200 and result of “no reservation exists for id:  the given id”>
 
  9)	Update One Existing Reservation
•	URL
http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/{reservationId}/{data}/{startTime}/{endTime}
e.g. http://localhost:8080/ProjectorScheduler/rest/ReservationService/reservations/1007/20180409/1230/1100
•	Method:
<PUT>
•	URL Params
<reservationId: integer; date: strings contain non-digit symbols; startTime: strings contain non-digit symbols; endTime: strings contain non-digit symbols >
•	Success Response:
<Code with 200 and return result of “given reservationId updated successfully with the new reservation details”> 
•	Error Response:
<Wrong URLs result in response of status code 404; trying to delete NOT existing reservation results in return code of 200 and result of “no reservation exists with given reservation id”>


   4.	Future Work: Add the functionality where if NO projectors are available for the requested time, return a list of available times for the same duration on the same day or next day. 

