package com.joaquimley.data

import com.joaquimley.transporteta.domain.model.Transport
import com.joaquimley.transporteta.domain.repository.TransportRepository
import io.reactivex.Completable
import io.reactivex.Observable

class TransportRepositoryImpl: TransportRepository {

    override fun requestTransportEta(transportCode: Int): Observable<Transport> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelTransportEtaRequest(transportCode: Int?): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransport(transportId: String): Observable<Transport> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTransport(transport: Transport): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTransport(transportList: List<Transport>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTransport(transport: Transport): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTransports(transport: List<Transport>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}