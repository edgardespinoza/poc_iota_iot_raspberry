const Iota = require('@iota/core')
const Converter = require('@iota/converter')

const iota = Iota.composeAPI({
  provider: "https://nodes.devnet.iota.org:443"//'https://testnet140.tangle.works:443'//http://34.73.20.142:14265'
})

//const remoteCurl = require('@iota/curl-remote')
//remoteCurl(iota, 'https://powbox.testnet.iota.org', 500)


/*
https://powbox.testnet.iota.org
 https://nodes.devnet.iota.org:443
"https://nodes.devnet.thetangle.org:443"  OK ,
"https://testnet140.tangle.works:443"   OK,
"http://p103.iotaledger.net:14700"  OK,


    http://p101.iotaledger.net:14700    X
    "https://nodes.devnet.iota.org:443"   X,
    "https://node.tangle.works:443"      x,
    "http://iota.bitfinex.com:80"    X,
    "http://eugene.iota.community:14265" X,
    "http://wallets.iotamexico.com:80" X,
  "http://mainnet.necropaz.com:14500"  X
*/

//WALLET ORIGIN
const seedOrigin ="CYSRWRHOKRXIOMUSGNDQPYWVJRPAULOXHYBRWAIMFRGINPVZKVOJRCSOWKHJCIZDPNADYIEYOSPO9QJIO"
//DESTINATION ACCOUNT
const addressDestination = 'TFWGNCOWGYTPLIIBXBVUHQQTBUZXIBJAOL9AFAUWSWEAIDATBKNYFNYSDQXOD9BTFMJYN9MYXIYX9ZD9Y'


const message = Converter.asciiToTrytes('transferencia IOTA')


const transfers = [{value: 10,
                    address: addressDestination,
                    message: message}]

iota.prepareTransfers(seedOrigin, transfers)
.then(trytes => iota.sendTrytes(trytes, (depth = 3), (mwm = 9)))
.then(bundle => {
    console.log(bundle)
}).catch(err => {
  console.log(err)
})
