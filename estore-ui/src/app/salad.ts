import { ActivatedRoute } from "@angular/router";
import { ProductService } from "./product.service";
import { UserService } from "./user.service";

export class Salad {
    salad: String = "0-0000000000000-0000000-000000-0-0-0-0";
    base: string = "0";
    vegetables: string = "0000000000000";
    fruits: string = "0000000";
    proteins: string = "000000";
    cheese: string = "0";
    dressing: string = "0";
    price: number = 0;
    calories: number = 0;
    ingredients: string[] = []
    message: string = "";
    username: string = (localStorage.getItem('sub')!);

    constructor(
        private userService: UserService
    ) {
        this.message = "";
    }

    baseArray: string[] = ["None", "Iceberg Lettuce", "Kale", "Spinach", "Salata Mix", "Romaine Lettuce", "Arugula", "Acardian Mix"];
    vegetableArray: string[] = ["Tomato", "Cucumber", "Carrot", "Jalapeno", "Bell Peppers", "Broccoli", "Edamame", "Cabbage", "Green Olives", "Black Olives", "Mushroom", "Red Onion", "Red Beans"];
    fruitsArray: string[] = ["Apple", "Grapes", "Mandarin Orange", "Pineapple", "Strawberry", "Raisins", "Dried Cranberries"];
    proteinsArray: string[] = ["Grilled Chicken", "Falafel", "Quinoa", "Steak", "Salmon", "Turkey"];
    cheeseArray: string[] = ["None", "Blue Cheese", "Feta Cheese", "Mixed Cheese", "Parmesan Cheese"];
    dressingArray: string[] = ["None", "Classic Caesar", "Buttermilk Ranch", "Olive Oil", "Honey Mustard", "Oil & Vinegar", "Balsamic Vinegar", "Jalapeno Avacado"];

    ingredientsDict: any = {
        "None": [0, 0], "Iceberg Lettuce": [0.5, 35], "Kale": ["0.5", 30], "Spinach": [0.5, 20], "Salata Mix": [0.5, 40], "Romaine Lettuce": [0.5, 30], "Arugula": [0.5, 15], "Acardian Mix": [0.5, 20], "Tomato": [0.5, 10], "Cucumber": [0.5, 5], "Carrot": [0.5, 5], "Jalapeno": [0.5, 5], "Bell Peppers": [0.5, 5], "Broccoli": [0.5, 5], "Edamame": [0.5, 25], "Cabbage": [0.5, 5], "Green Olives": [0.5, 40], "Black Olives": [0.5, 35], "Mushroom": [0.5, 5], "Red Onion": [0.5, 10], "Red Beans": [0.5, 20], "Apple": [0.5, 15], "Grapes": [0.5, 20], "Mandarin Orange": [0.5, 20], "Pineapple": [0.5, 15], "Strawberry": [0.5, 10], "Raisins": [0.5, 100], "Dried Cranberries": [0.5, 70], "Grilled Chicken": [0.5, 190], "Falafel": [0.5, 130], "Quinoa": [0.5, 100], "Steak": [0.5, 190], "Salmon": [0.5, 190], "Turkey": [0.5, 90], "Blue Cheese": [0.5, 60], "Feta Cheese": [0.5, 45], "Mixed Cheese": [0.5, 40], "Parmesan Cheese": [0.5, 50], "Classic Caesar": [0.5, 290], "Buttermilk Ranch": [0.5, 230], "Olive Oil": [0.5, 130], "Honey Mustard": [0.5, 270], "Oil & Vinegar": [0.5, 280], "Balsamic Vinegar": [0.5, 20], "Jalapeno Avacado": [0.5, 164]
    };

    /**
   * Gets the information of the currently logged in {@linkplain User user}
   */
    getUserSalad(): void {
        this.userService.getSalad(this.username).subscribe(salad => {
            this.salad = salad.toString();
            console.log(this.salad);
            var saladArray = this.salad.split("-");
            this.base = saladArray[0];
            this.vegetables = saladArray[1];
            this.fruits = saladArray[2];
            this.proteins = saladArray[3];
            this.cheese = saladArray[4];
            this.dressing = saladArray[5];
            this.price = Number(saladArray[6]);
            this.calories = Number(saladArray[7]);


            this.ingredients.push(this.baseArray[Number(this.base)]);
            for (let i = 0; i < this.vegetables.length; i++) {
                if (this.vegetables.charAt(i) == "1") {
                    this.ingredients.push(this.vegetableArray[i]);
                }
            }
            for (let i = 0; i < this.fruits.length; i++) {
                if (this.fruits.charAt(i) == "1") {
                    this.ingredients.push(this.fruitsArray[i]);
                }
            }
            for (let i = 0; i < this.proteins.length; i++) {
                if (this.proteins.charAt(i) == "1") {
                    this.ingredients.push(this.proteinsArray[i]);
                }
            }
            this.ingredients.push(this.cheeseArray[Number(this.cheese)]);
            this.ingredients.push(this.dressingArray[Number(this.dressing)]);
            this.ingredients = this.ingredients.filter(function (el) {
                return el != "None";
            }
            );
        });
    }

    /**
     * Handles the action of adding a product to a customer's shopping cart
     * @param product The {@linkplain Product product} to add
     * @param quantity The quantity to add to their cart
     */
    addToCart(): void {
        /**
         * Takes in a product to add to the user's cart
         */
        var username = (localStorage.getItem('sub')!);
        this.salad = this.base + "-" + this.vegetables + "-" + this.fruits + "-" + this.proteins + "-" + this.cheese + "-" + this.dressing + "-" + this.price + "-" + this.calories;
        this.userService.setSalad(username, this.salad.toString()).subscribe(() => console.log(this.salad));
        this.message = "Salad Saved In Cart Successfully!";
    }

    initCheckboxes() {
        this.username = (localStorage.getItem('sub')!);
        this.userService.getSalad(this.username).subscribe(salad => {
            this.salad = salad.toString();
            console.log(this.salad);
            var saladArray = this.salad.split("-");
            this.base = saladArray[0];
            this.vegetables = saladArray[1];
            this.fruits = saladArray[2];
            this.proteins = saladArray[3];
            this.cheese = saladArray[4];
            this.dressing = saladArray[5];
            this.price = Number(saladArray[6]);
            this.calories = Number(saladArray[7]);

            const select = document.getElementById('baseSelect') as HTMLSelectElement | null;
            if (select != null) {
                select.selectedIndex = Number(this.base);
            }

            for (var i = 0; i < 13; i++) {
                if (this.vegetables.charAt(i) == "1") {
                    document.getElementById(i.toString())!.setAttribute("checked", "true");
                }
            }
            for (var i = 0; i < 7; i++) {
                if (this.fruits.charAt(i) == "1") {
                    document.getElementById((i + 13).toString())!.setAttribute("checked", "true");
                }
            }
            for (var i = 0; i < 6; i++) {
                if (this.proteins.charAt(i) == "1") {
                    document.getElementById((i + 20).toString())!.setAttribute("checked", "true");
                }
            }

            const select2 = document.getElementById('cheeseSelect') as HTMLSelectElement | null;
            if (select2 != null) {
                select2.selectedIndex = Number(this.cheese);
            }

            const select3 = document.getElementById('dressingSelect') as HTMLSelectElement | null;
            if (select3 != null) {
                select3.selectedIndex = Number(this.dressing);
            }

            document.getElementById("price")!.innerHTML = "Total Price: $" + this.price;
            document.getElementById("calories")!.innerHTML = "Total Calories: " + this.calories;
        });
    }

    clearSalad() {
        this.salad = "0-0000000000000-0000000-000000-0-0-0-0";
        this.base = "0";
        this.vegetables = "0000000000000";
        this.fruits = "0000000";
        this.proteins = "000000";
        this.cheese = "0";
        this.dressing = "0";
        this.price = 0;
        this.calories = 0;

        const select = document.getElementById('baseSelect') as HTMLSelectElement | null;
        if (select != null) {
            select.selectedIndex = 0;
        }

        for (var i = 0; i < 26; i++) {
            document.getElementById(i.toString())!.removeAttribute("checked");
        }

        const select2 = document.getElementById('cheeseSelect') as HTMLSelectElement | null;
        if (select2 != null) {
            select2.selectedIndex = 0;
        }

        const select3 = document.getElementById('dressingSelect') as HTMLSelectElement | null;
        if (select3 != null) {
            select3.selectedIndex = 0;
        }

        this.calculate();
    }

    calculate() {
        this.price = 0;
        this.calories = 0;

        this.price += this.ingredientsDict[this.baseArray[Number(this.base)]][0];
        this.calories += this.ingredientsDict[this.baseArray[Number(this.base)]][1];

        for (var i = 0; i < 13; i++) {
            if (this.vegetables.charAt(i) == "1") {
                this.price += this.ingredientsDict[this.vegetableArray[i]][0];
                this.calories += this.ingredientsDict[this.vegetableArray[i]][1];
            }
        }
        for (var i = 0; i < 7; i++) {
            if (this.fruits.charAt(i) == "1") {
                this.price += this.ingredientsDict[this.fruitsArray[i]][0];
                this.calories += this.ingredientsDict[this.fruitsArray[i]][1];
            }
        }
        for (var i = 0; i < 6; i++) {
            if (this.proteins.charAt(i) == "1") {
                this.price += this.ingredientsDict[this.proteinsArray[i]][0];
                this.calories += this.ingredientsDict[this.proteinsArray[i]][1];
            }
        }

        this.price += this.ingredientsDict[this.cheeseArray[Number(this.cheese)]][0];
        this.calories += this.ingredientsDict[this.cheeseArray[Number(this.cheese)]][1];

        this.price += this.ingredientsDict[this.dressingArray[Number(this.dressing)]][0];
        this.calories += this.ingredientsDict[this.dressingArray[Number(this.dressing)]][1];

        this.salad = this.base + "-" + this.vegetables + "-" + this.fruits + "-" + this.proteins + "-" + this.cheese + "-" + this.dressing + "-" + this.price + "-" + this.calories;
        document.getElementById("price")!.innerHTML = "Total Price: $" + this.price;
        document.getElementById("calories")!.innerHTML = "Total Calories: " + this.calories;
    }

    vegetableChange(name: string, i: number, event: any) {
        if (event.target.checked) {
            this.vegetables = this.vegetables.substring(0, i) + "1" + this.vegetables.substring(i + 1);
        }
        else {
            this.vegetables = this.vegetables.substring(0, i) + "0" + this.vegetables.substring(i + 1);
        }
        this.calculate();
    }

    fruitChange(name: string, i: number, event: any) {
        if (event.target.checked) {
            this.fruits = this.fruits.substring(0, i) + "1" + this.fruits.substring(i + 1);
        }
        else {
            this.fruits = this.fruits.substring(0, i) + "0" + this.fruits.substring(i + 1);
        }
        this.calculate();
    }

    proteinChange(name: string, i: number, event: any) {
        if (event.target.checked) {
            this.proteins = this.proteins.substring(0, i) + "1" + this.proteins.substring(i + 1);
        }
        else {
            this.proteins = this.proteins.substring(0, i) + "0" + this.proteins.substring(i + 1);
        }
        this.calculate();
    }

    deleteSalad(): void {
        this.userService.setSalad(this.username, "0-0000000000000-0000000-000000-0-0-0-0").subscribe(() => this.salad = "0-0000000000000-0000000-000000-0-0-0-0");
        this.base = "0";
        this.vegetables = "0000000000000";
        this.fruits = "0000000";
        this.proteins = "000000";
        this.cheese = "0";
        this.dressing = "0";
        this.price = 0;
        this.calories = 0;
    }
}