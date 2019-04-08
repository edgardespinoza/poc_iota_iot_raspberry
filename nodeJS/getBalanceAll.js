const Iota = require('@iota/core')
const Converter = require('@iota/converter');
const util = require('util')
// Create a new instance of the IOTA class object.
// Use 'provider' variable to specify which Full Node to talk to

 // provider: 'https://testnet140.tangle.works:443'
   //provider: 'https:/nodes.devnet.iota.org:443'

//https://powbox.testnet.iota.org`, 50
const iota = Iota.composeAPI({
  provider: "https://nodes.devnet.iota.org:443"//'https://testnet140.tangle.works:443'//'http://34.73.20.142:14265'
})
/*
iota.getNodeInfo()
    .then(info => console.log(info))
    .catch(err => {'Request error: ${error.message}'})
*/

const seed ="WFNMFLJEYUSONQPGNZASVKAMRCPEEOWHNWDPVOXZSOWC9QDLISOR9LPXPILBNLITSJYGJETCRJBUFUKY9"
const seed1 = "CYSRWRHOKRXIOMUSGNDQPYWVJRPAULOXHYBRWAIMFRGINPVZKVOJRCSOWKHJCIZDPNADYIEYOSPO9QJIO"


iota.getAccountData(seed, {
   start: 0,
   security: 2
})
  .then(accountData => {
    const { addresses, inputs, transactions, balance } = accountData

    console.log('\x1b[32m WALLET ::: %s\x1b[0m', seed);
    console.log(addresses);
    var total = 0;
    var values = "";

    iota.getBalances(addresses ,100)
      .then(({ balances }) => {

        balances.forEach(function (item) {
          total  = total + item;
          values = values + ","+item;
        })

          console.log(values)
           console.log(total)

        //console.log(values)
    //    console.log(total)
      })
      .catch(err => {
        console.log(err)
      })

  })
  .catch(err => {
    console.log(err);
  })


 iota.getAccountData(seed1, {
     start: 0,
     security: 2
  })
    .then(accountData => {
      const { addresses, inputs, transactions, balance } = accountData
      console.log('\x1b[32m WALLEY ::: %s\x1b[0m', seed1);
      console.log(addresses);

      var total = 0;
        var values = "";

      iota.getBalances(addresses ,100)
        .then(({ balances }) => {

          balances.forEach(function (item) {
          total  = total + item;
          values = values + ","+item;
        })

            console.log(values)
           console.log(total)

        })
        .catch(err => {
          console.log(err)
        })
    })
    .catch(err => {
      console.log(err);
    })
