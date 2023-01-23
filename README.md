# react-native-magnati-pos

All POS functions integrated in library ready to use

## Installation

```sh
npm install react-native-magnati-pos
```

## Usage

Do make sure the application has Bluetooth connect permission, to setup add below permission in AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
```

To trigger initialize function of POS call  initializePOS as given in below example 
```js
import { initializePOS } from 'react-native-magnati-pos';

// ...

const result = await  initializePOS({
      transactionTimeout: 120,
      uuid: 'xxxxx-xxxxx-xxxxxx-xxxxx',
      connectionTimeout: 30,
      settlementTimeout: 300,
      enableTrace: false,
    });
```


To Start transaction mode of POS call  startTransactionMode as given in below example 
```js
import { startTransactionMode } from 'react-native-magnati-pos';

// ...

const result = await  startTransactionMode();
```


To trigger payment auth function of POS call  initializePayment as given in below example 
```js
import { initializePayment } from 'react-native-magnati-pos';

// ...

const result = await  initializePayment(transactionAmount:number,TID:number, ecrn:number)
```
While calling above function do keep in mind below details:
- The transaction amount is with assumed decimals. Example: 100 will be taken as DHS 1.00
- TID is the terminal id of pos
- ecrn is a Transaction number for a particular batch. 

To Stop transaction mode of POS call  stopTransactionMode as given in below example 
```js
import { stopTransactionMode } from 'react-native-magnati-pos';

// ...

const result = await  stopTransactionMode();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
