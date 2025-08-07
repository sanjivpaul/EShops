# E-Shop

### Flow

1. Create Models (Entity)
    1. `Product`
2. Create Services
    1. like `ProductService`
    2. product service interface i.e `IProductService`
    3. implement `IProductService` in `ProductService`
    4. import all methods of `IProductService` interface in `ProductService`

3. Create Repository
    1. create `ProductRepository` and add query for `ProductService`
