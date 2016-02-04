<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:ent="http://wandrell.com/example/ws/entity">
	<soapenv:Header>
		<wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1">
			<wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd">
				<wsse:Username>${user}</wsse:Username>
				<wsse:Password>${password}</wsse:Password>
			</wsse:UsernameToken>
		</wsse:Security>
	</soapenv:Header>
    <soapenv:Body>
        <ent:getEntityRequest>
            <ent:id>1</ent:id>
        </ent:getEntityRequest>
    </soapenv:Body>
</soapenv:Envelope>