package exceptions

class ProductNotFound(id: Int) : EntityNotFoundException("product", id)