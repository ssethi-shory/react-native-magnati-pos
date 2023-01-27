import React from 'react';
import { useEffect, useState } from 'react';
import { Platform, TextInput } from 'react-native';

import {
  StyleSheet,
  View,
  Text,
  Button,
  PermissionsAndroid,
  Alert,
} from 'react-native';
import {
  initializePOS,
  startTransactionMode,
  stopTransactionMode,
  initializePayment,
  getTID_MID,
} from 'react-native-magnati-pos';

const App = () => {
  const [isLoading, setLoading] = useState(false);
  const [amount, setAmount] = useState('');
  useEffect(() => {
    setLoading(true);
    requestBluetoothPermission();
    initializePOS({
      transactionTimeout: 120,
      uuid: '00001101-0000-1000-8000-00805F9B34FB',
      connectionTimeout: 30,
      settlementTimeout: 300,
      enableTrace: false,
    })
      .then((res: any) => console.log(res))
      .catch((ex: any) => console.log(ex))
      .finally(() => {
        setLoading(false);
      });
  }, []);

  const requestBluetoothPermission = async () => {
    try {
      if (Platform.OS === 'android') {
        const granted = await PermissionsAndroid.request(
          //@ts-ignore
          PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
          {
            title: 'Connect to nearby bluetooth devices',
            message: 'POS machine are detected by bluetooth',
            buttonNeutral: 'Ask Me Later',
            buttonNegative: 'Cancel',
            buttonPositive: 'OK',
          }
        );
        if (granted === PermissionsAndroid.RESULTS.GRANTED) {
          console.log('Bluetooth enabled');
        } else {
          console.log('Bluetooth permission denied');
        }
      }
    } catch (err) {
      console.warn(err);
    }
  };
  const getTidMid = () => {
    startTransactionMode()
      .then((startRes) => {
        console.log('starting res', startRes);
        if (startRes.success) {
          setTimeout(() => {
            getTID_MID()
              .then((res: any) => console.log(res))
              .catch((ex: any) => console.log(ex))
              .finally(() => {
                stopTransactionMode()
                  .then((stopRes) => console.log(stopRes))
                  .catch((ex) => console.log(ex));
              });
          }, 3000);
        }
      })
      .catch((ex) => {
        console.log(ex);
      });
  };
  const allSteps = () => {
    let transactionAmount = Number(amount);
    if (transactionAmount > 1) {
      console.log('starting');
      setLoading(true);
      startTransactionMode()
        .then((startRes) => {
          console.log('starting res', startRes);
          if (startRes.success) {
            setTimeout(() => {
              console.log('initialize');
              initializePayment(transactionAmount * 100, '00283933', '1234')
                .then((res: IMagnatiAuthResponse) => {
                  console.log('initialize res', res);
                  if (!res.success)
                    Alert.alert(`Error - ${res?.code} - ${res?.message}`);
                  else Alert.alert(`Success - ${res?.code} - ${res?.message}`);
                  console.log(res?.data);
                })
                .catch((ex) => console.log(ex))
                .finally(() => {
                  setLoading(false);
                  stopTransactionMode()
                    .then((stopRes) => console.log(stopRes))
                    .catch((ex) => console.log(ex))
                    .finally(() => {
                      setLoading(false);
                    });
                });
            }, 20000);
          } else {
            setLoading(false);
          }
        })
        .catch((ex) => {
          setLoading(false);
          console.log(ex);
        });
    } else {
      Alert.alert(`Error - Amount should be greater than 1`);
    }
  };
  return (
    <View style={styles.container}>
      {isLoading && <Text>Loading... </Text>}
      <TextInput
        value={amount}
        style={styles.input}
        onChangeText={setAmount}
        placeholder="useless placeholder"
        keyboardType="numeric"
      />
      <Button disabled={isLoading} title="All Steps" onPress={allSteps} />
      <Button title="GET TID MID" onPress={getTidMid} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
});

export default App;
