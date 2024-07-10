import { test, expect } from '@playwright/test';

test('should create product', async ({ request }) => {
    // Create a product
    const response = await request.post('/api/v1/catalog', {
        data: {
            "name": "Mouse",
            "description": "Mouse",
            "price": 100.00,
            "availableStock": 1
        },
    });
    
    // Expect the response to have a status code of 201
    expect(response.ok()).toBeTruthy();

    expect(await response.json()).toEqual(expect.objectContaining({
        name: "Mouse",
        price: 100.00
    }));
})