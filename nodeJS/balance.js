// Require the IOTA libraries
const Iota = require('@iota/core');
const Converter = require('@iota/converter');
// Create a new instance of the IOTA object
// Use the `provider` field to specify which IRI node to connect to
const iota = Iota.composeAPI({
provider: 'https://nodes.devnet.thetangle.org:443'//'http://34.73.20.142:14265'//'https://nodes.devnet.iota.org:443'
});
//'https://34.73.20.142:14700'//'
const address = "DEUUWNOQHFXPTJQVUXSIEVLTDDSTUAHXSYGWDJQJEUJKZJATYBYOH9QFCQNZUTGISEIIROTIALOQVGXLA";
const address1 = "HJGZXTQEABMGQHCYIYHDVXNKJTEICWZVEWZMQOFGEYKHW9PBHQUGHGFGLQRTPDDXMJDTQZCQSLFUUVLUD";
const address2 ="EMECGC9EUHJDIHBOGOXGKCL9YPTKMAMNYZPESKVLHLDYSYEZLHQREWOJDOLHALNKYFVIRDHHBTBCQNMYZ";

iota.getBalances([address, address1, address2], 100)
  .then(({ balances }) => {
     console.log(balances);
  })
  .catch(err => {
    console.log(err)
  })
