$start = 1
$finish = 30
$uri = "http://localhost:9090/api/v1/balance/secret"
for ($z=$start; $z -le $finish; $z++)
{
    $response = Invoke-RestMethod -Uri $uri -Method Get -ContentType plain/text
    Write-Host "$z Server response: $response"
}