# https://stackoverflow.com/questions/25448449/declaring-powershell-variable
$keycloak = 'keycloak-12.0.1'
$url = "https://github.com/keycloak/keycloak/releases/download/12.0.1/${keycloak}.zip"
$curDir = Get-Location
$keycloakFolder = Join-Path $curDir $keycloak

# https://stackoverflow.com/questions/31879814/check-if-a-file-exists-or-not-in-windows-powershell
if (Test-Path "${keycloak}.zip" ) {
    Write-Output "${keycloak}.zip exist"
}
else {
    # https://stackoverflow.com/questions/4926060/powershell-runspace-problem-with-downloadfileasync
    # https://stackoverflow.com/questions/25361736/how-to-cancel-wait-event-by-ctrl-c-instead-of-cancelling-whole-script-in-powersh
    $client = New-Object System.Net.WebClient 
    [Console]::TreatControlCAsInput = $true;
    try {
        Register-ObjectEvent $client DownloadProgressChanged -Action {
            Write-Progress -Activity "Downloading $fileName" -Status `
            ("Completed {0}%" -f [math]::Round($eventargs.BytesReceived * 100 / $eventargs.TotalBytesToReceive, 2)) `
        }
        Register-ObjectEvent $client DownloadFileCompleted -SourceIdentifier Finished
        $client.DownloadFileAsync($url, "${keycloakFolder}.zip")
        Clear-Host
        Write-Output "Download ${keycloak}"
        Wait-Event -SourceIdentifier Finished
    }
    finally {
        $client.Dispose()
    }
    
}
if (Test-Path $keycloakFolder) {
    Write-Output "${keycloak}\ exist"    
}
else {
    $ProgressPreference = 'SilentlyContinue'
    Write-Output "Start extracting ${keycloak}.... Please do not turn off this console. If you do please remove ${keycloak}\"
    # https://stackoverflow.com/questions/27768303/how-to-unzip-a-file-in-powershell
    Expand-Archive $keycloakFolder $curDir
    $ProgressPreference = 'Continue'
    Write-Output "Extracting ${keycloak} Done."
}
# https://stackoverflow.com/questions/13783759/concatenate-network-path-variable
# https://stackoverflow.com/questions/3592851/executing-a-command-stored-in-a-variable-from-powershell
& (Join-PAth $keycloakFolder bin\add-user-keycloak.bat) -r master -u admin -p admin
& (Join-PAth $keycloakFolder bin\standalone.bat) "-Djboss.socket.binding.port-offset=10" "-Dkeycloak.migration.action=import" "-Dkeycloak.migration.provider=singleFile" "-Dkeycloak.migration.file=$curDir/ostock-realm-config.json" "-Dkeycloak.migration.strategy=OVERWRITE_EXISTING"
