# Drones

## Introduction

This is a service delivered via REST API that allows clients to communicate with the drones.
It is initialized by some data on the data.sql file.

## Build commands:

- Build: 
- Run: ./mvnw spring-boot:run
- Test: ./mvnw test


## Documentation:

Content-type: application.json.

[Link to postman json API tests:](https://www.getpostman.com/collections/5f01a8ff059f8c977a32)

1. localhost:8080/drone
- register drone (POST): Request body is given below:
```
    {
    "model": {
        "id": 4,
        "value": 500,
        "name": "HEAVY_WEIGHT"
    }
}
```
- find drone by id(GET): localhost:8080/drone/{id}
- update drone (PUT): Request body is given below:
```

    {
    "id": 5,
    "serialNumber": {
        "id": 5,
        "value": "PEGEN-BEU3S-OSO0A-2W1ZX-YRGYK-ULCLG"
    },
    "batteryCapacity": 100,
    "model": {
        "id": 5,
        "value": 500,
        "name": "MIDDLE_WEIGHT"
    },
    "state": "IDLE",
    "medications": []
}

```
- Delete drone (DELETE): localhost:8080/drone/{id}
- Load Medications from drone(GET): localhost:8080/drone/load?droneId=3&medicationIds=1,2,3. It accepts drone id and list of medication ids
- Get drone from loaded medications: localhost:8080/drone/fromLoadedMedications/{droneId}. It checks loaded medications for a given drone.
- Find available drones(GET): localhost:8080/drone/available/{totalMedicationWeight}. It returns a list of drones that are available, given the total medication weight to be loaded.
- Get Battery level: localhost:8080/batteryLevel/{id}. Get drone battery level

2. localhost:8080/med
- register medication(POST): Request body given:
```
    {
    "name": "paracetamol",
    "weight": 250
}
```
- Upload medication image(PUT): localhost:8080/med/{id}
Content-type: multipart/form-data. It accepts a request param of type file

- Get Medication (GET): localhost:8080/med/{id}
- Update Medication (PUT): localhost:8080/med. Request body given: 
```
    {
    "id": 2,
    "name": "ibuprofen",
    "weight": 70
}
```
- Delete medication (DELETE): localhost:8080/med/{id};