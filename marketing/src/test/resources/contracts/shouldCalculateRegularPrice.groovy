import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method('POST')
        url('/specials/{id}/calculate')
        body([
                unitPrice: 40.0,
                unitCount: 2
        ])
        headers {
            header('Content-Type', 'application/json')
        }
    }
    response {
        status(200)
        body([
                totalPrice: 80.0
        ])
        headers {
            header('Content-Type', 'application/json')
        }
    }
}
