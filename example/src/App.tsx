import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { initializePOS } from 'react-native-magnati-pos-integration-library';

export default function App() {
  React.useEffect(() => {
    initializePOS({
      //       transactionTimeout: 120,
      uuid: '00001101-0000-1000-8000-00805F9B34FB',
      //       connectionTimeout: 30,
      //       settlementTimeout: 300,
      enableTrace: false,
    })
      .then((res: any) => console.log(res))
      .catch((ex: any) => console.log(ex));
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: </Text>
    </View>
  );
}

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
});
