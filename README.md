# KP Parser
Background parser films descriptions from kinopoisk and imdb

#### Usage:  
    java -jar kpparser.jar --scanFilesFolder=/path/to/movies/ [optional arguments]

###### Available arguments:
      --scanFilesFolder:             folder to scan movie files (required) 
      --locationPattern:             file mask to scan movie files (*.mov by default)
      --inputSystem:                 comma separated readers list. Use first accessible reader 
                                     possible values: KinoPoisk, rutor, imdb 
      --outputFormat:                possible values TEXT, JSON, XML  (TEXT by default)
      --saveDescriptionsFolder:      folder to save movie descriptions (scanFilesFolder bu default)
      --useProxy:                    (true/false) use proxies list to scan (false by default)
      --proxyListFile:               path to plain text file with proxies list 
      --rescanExistingDescriptions:  (true/false) omit or scan again already existing descriptions  (false by default)
      --blocksQty:                   how many items to process before save it to the output (3 by default)
