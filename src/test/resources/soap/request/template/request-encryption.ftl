<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" SOAP-ENV:mustUnderstand="1">
         <wsse:BinarySecurityToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" EncodingType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary" ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3" wsu:Id="XWSSGID-1456388921667442630466">${secToken}</wsse:BinarySecurityToken>
         <xenc:EncryptedKey xmlns:xenc="http://www.w3.org/2001/04/xmlenc#" Id="XWSSGID-1456388922349-1655386320">
            <xenc:EncryptionMethod Algorithm="http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p" />
            <ds:KeyInfo xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
               <wsse:SecurityTokenReference>
                  <wsse:Reference URI="#XWSSGID-1456388921667442630466" ValueType="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3" />
               </wsse:SecurityTokenReference>
            </ds:KeyInfo>
            <xenc:CipherData>
               <xenc:CipherValue>${cipherHeader}</xenc:CipherValue>
            </xenc:CipherData>
         </xenc:EncryptedKey>
         <xenc:ReferenceList xmlns:xenc="http://www.w3.org/2001/04/xmlenc#">
            <xenc:DataReference URI="#XWSSGID-14563889223511808410725" />
         </xenc:ReferenceList>
      </wsse:Security>
   </SOAP-ENV:Header>
   <SOAP-ENV:Body>
      <xenc:EncryptedData xmlns:xenc="http://www.w3.org/2001/04/xmlenc#" Id="XWSSGID-14563889223511808410725" Type="http://www.w3.org/2001/04/xmlenc#Content">
         <xenc:EncryptionMethod Algorithm="http://www.w3.org/2001/04/xmlenc#aes128-cbc" />
         <ds:KeyInfo xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
            <wsse:SecurityTokenReference xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
               <wsse:Reference URI="#XWSSGID-1456388922349-1655386320" />
            </wsse:SecurityTokenReference>
         </ds:KeyInfo>
         <xenc:CipherData>
            <xenc:CipherValue>${cipherBody}</xenc:CipherValue>
         </xenc:CipherData>
      </xenc:EncryptedData>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>