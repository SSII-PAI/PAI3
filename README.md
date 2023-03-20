# PAI3
PAI-3. BYODSEC-BRING YOUR OWN DEVICE SEGURO PARA UNA UNIVERSIDAD PÚBLICA USANDO ROAD WARRIOR VPN SSL

## Creación del certificado

```powershell
New-SelfSignedCertificate -CertStoreLocation Cert:\LocalMachine\My -DnsName "grupo11.ssii.us" -FriendlyName "grupo11" -NotAfter (Get-Date).AddYears(10)
```

Luego he exportado este certificado desde la herramienta de certificados de Windows. Esto al final no lo usamos pero está apuntado por si hace falta más adelante.