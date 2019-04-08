/*const Iota = require('@iota/core')
const Converter = require('@iota/converter')

const iota = Iota.composeAPI({
  provider: 'https://nodes.devnet.iota.org:443'
})

const seed ="ASHRQEC9EWQJIRUEDGJHXQAREYDRVXPTFNVOZTOFJN9YXCGIWSVEIPQCFNWSXTEVAPSOM9XEMJCWWNSDM"

iota.getNewAddress(seed, {
  index: 0,
  total: 1,
  security: 2})
  .then(address => {
    console.log(address)
  })
  .catch(err => {
    console.log(err)
  })
*/

  // Require the IOTA library
  const Iota = require('@iota/core');

  // Create a new instance of the IOTA object
  // Use the `provider` field to specify which IRI node to connect to
  const iota = Iota.composeAPI({
  provider: 'https://nodes.devnet.iota.org:443'//'https://nodes.thetangle.org:443'
  });

  const seed =
  'CYSRWRHOKRXIOMUSGNDQPYWVJRPAULOXHYBRWAIMFRGINPVZKVOJRCSOWKHJCIZDPNADYIEYOSPO9QJIO';

  // Create an address with index 0 and security level 2
  iota.getNewAddress(seed, {index: 0, security: 2})
  .then(address => console.log(address))
  .catch(err => {
    console.log(err)
  })
