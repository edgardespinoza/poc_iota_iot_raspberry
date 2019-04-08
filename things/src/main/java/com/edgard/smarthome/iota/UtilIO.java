package com.edgard.smarthome.iota;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jota.IotaAPI;
import jota.dto.response.GetBalancesAndFormatResponse;
import jota.dto.response.GetNewAddressResponse;
import jota.dto.response.GetNodeInfoResponse;
import jota.dto.response.SendTransferResponse;
import jota.model.Input;
import jota.model.Transaction;
import jota.model.Transfer;

public class UtilIO {
    private static final String TEST_MESSAGE = "JUSTANOTHERIOTATEST";
    private static final String TEST_TAG = "IOTAJAVASPAM999999999999999";
    private static final int MIN_WEIGHT_MAGNITUDE = 9;
    private static final int DEPTH = 3;

   public static String TEST_SEED1 = "WFNMFLJEYUSONQPGNZASVKAMRCPEEOWHNWDPVOXZSOWC9QDLISOR9LPXPILBNLITSJYGJETCRJBUFUKY9";
    public static String TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_2 = "EPRCSTFOAH9CUBYNGTC99WEMNOINURJMMRMNYEIYLTNGGDWLKMIXJCTNCZKOZIVZPQPMQNYNB9ZYAQHV9";
    public static String SERVER = "nodes.devnet.iota.org";
    public static int TX_VALUE = 1;

    static IotaAPI iotaAPI = new IotaAPI.Builder()
            .protocol("https")
            .host(SERVER)
            .port("443")
            .build();


    public static void connectIOTA() throws Exception {


        List<Input> inputlist = new ArrayList<>();
        List<Transfer> transfers = new ArrayList<>();

        GetBalancesAndFormatResponse rsp = iotaAPI.getInputs(TEST_SEED1, 2, 4, 20, 1);

        inputlist.addAll(rsp.getInputs());

        transfers.add(new Transfer(TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_2, TX_VALUE, TEST_MESSAGE, TEST_TAG));

        SendTransferResponse str = iotaAPI.sendTransfer(TEST_SEED1, 2, DEPTH, MIN_WEIGHT_MAGNITUDE, transfers, inputlist, null, true, true, null);

        Log.e("UtilIO","connectIOTA.="+ str.getTransactions().size());
        Log.e("UtilIO","connectIOTA="+ str.getSuccessfully().length);

    }

    public static void connectNode()throws Exception{


        GetNodeInfoResponse response = iotaAPI.getNodeInfo();
        Log.d("TAG", response.getAppName());
    }

    public static int  shouldSendTransferWithoutInputs() throws Exception {
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer(TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_2, 100, TEST_MESSAGE, TEST_TAG));
        SendTransferResponse str = iotaAPI.sendTransfer(TEST_SEED1, 3, DEPTH, MIN_WEIGHT_MAGNITUDE, transfers, null, null, false, true, null);
        Log.e("TAG",""+ str.getTransactions().size());
        Log.e("TAG",""+ str.getSuccessfully().length);
        return  str.getTransactions().size();
    }


    public static void shouldPrepareTransfer() throws Exception {


       List<Input> inputlist = new ArrayList<>();
        List<Transfer> transfers = new ArrayList<>();

        GetBalancesAndFormatResponse rsp = iotaAPI.getInputs(TEST_SEED1, 2, 0, 5, 0);

        inputlist.addAll(rsp.getInputs());

        transfers.add(new Transfer(TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_2, 1, TEST_MESSAGE, TEST_TAG));
        List<String> trytes = iotaAPI.prepareTransfers(TEST_SEED1, 2, transfers, null, inputlist, null,true);





        String[] trytesArray = new String[trytes.size()];
        trytesArray = trytes.toArray(trytesArray);

        List<Transaction> lista = iotaAPI.sendTrytes(trytesArray, DEPTH, MIN_WEIGHT_MAGNITUDE, null);
        Log.e("TAG", "lista OK " + lista.size());
    }

   /* public void shouldCreateANewAddressWithoutChecksum() throws Exception {
        final GetNewAddressResponse res1 = iotaAPI.getAddressesUnchecked(TEST_SEED1, 1, false, 0, 5);
        assertThat(res1.getAddresses().get(0), Is.is(TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_1));

        final GetNewAddressResponse res2 = iotaAPI.getAddressesUnchecked(TEST_SEED1, 2, false, 0, 5);
        assertThat(res2.getAddresses().get(0), Is.is(TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_2));

        final GetNewAddressResponse res3 = iotaAPI.getAddressesUnchecked(TEST_SEED1, 3, false, 0, 5);
        assertThat(res3.getAddresses().get(0), Is.is(TEST_ADDRESS_WITHOUT_CHECKSUM_SECURITY_LEVEL_3));
    }*/
}
