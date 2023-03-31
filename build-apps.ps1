#building applications
gradle clean build --build-file "money_transfer/build.gradle"
mvn clean install --file "reporting/pom.xml"
gradle clean build --build-file "auth/build.gradle"

#deploy applications to docker
docker-compose up -d --build

#wait for Kafka Connect
$waitSeconds = 10
Write-Output "Waiting for Kafka Connect container: $waitSeconds seconds"
Start-Sleep -Seconds $waitSeconds

#check for existing Kafka connectors
$uri = "http://localhost:8083/connectors"
$currentStatusCode = -1
$sourceConnectorName = "source-connector"

do {
    try {
        $response = Invoke-WebRequest -Uri "$uri/$sourceConnectorName" -Method Get -ContentType application/json
        $currentStatusCode = $response | Select-Object -Expand StatusCode
        if ($currentStatusCode -eq 200) {
            Write-Output "Connectors already exists"
            Read-Host "Press any key to continue..."
            Exit
        }

    } catch {
        $currentStatusCode = $_.Exception.Response.StatusCode.value__
        Write-Output "Error. Status code=$currentStatusCode"
        Write-Output "Waiting for Kafka Connect container: $waitSeconds seconds"
        Start-Sleep -Seconds $waitSeconds
    }
} while ($currentStatusCode -ne 404)

#post connectors for Kafka Connect
$uploadSourceFilePath = "./money_transfer/context/kafka-connect/debeziumConnectionConfig.json"
$uploadSyncFilePath = "./reporting/context/kafka-connect/confluentSinkConnector.json"

try {
    Invoke-RestMethod -Uri $uri -Method Post -InFile $uploadSourceFilePath -ContentType application/json
    Invoke-RestMethod -Uri $uri -Method Post -InFile $uploadSyncFilePath -ContentType application/json
} finally {
    $currentDateTime = Get-Date -DisplayHint DateTime
    Write-Output "Deploy finished. Current time: $currentDateTime"
}