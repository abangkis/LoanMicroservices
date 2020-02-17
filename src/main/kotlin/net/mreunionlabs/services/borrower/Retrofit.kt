package net.mreunionlabs.services.borrower

import com.google.gson.GsonBuilder
import net.mreunionlabs.services.loan.Loan
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface LoanService {
    @GET("loan")
    suspend fun getLoan(): Loan
}

object RetrofitHelper {
    private var retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8090/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()


    private var service = retrofit.create(LoanService::class.java)

    fun getService() = service
}
