import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class domaci22 {


    private WebDriver driver;
    private Faker faker;
    private WebDriverWait wait;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "F:/chromedriver.exe");
        faker = new Faker();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @BeforeMethod
    public void beforeTest() {
        driver.get("https://vue-demo.daniel-avellaneda.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    //Testovi
    /*
    Test 1: Verifikovati da se u url-u stranice javlja ruta "/login".
    Verifikovati da atribut type u polju za unos email
    ima vrednost "email" i za password da ima atribut type "password.
*/
    @Test // Testiramo URL i input types
    public void testInput() {
        WebElement login = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]/span"));
        login.click();
        System.out.println("Ispis");
        final String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        Assert.assertTrue(currentUrl.contains("/login"));
        WebElement emailField = driver.findElement(By.id("email"));
        String ValueOfEmailType = emailField.getAttribute("type");
        String ValueOfPasswordType = driver.findElement(By.id("password")).getAttribute("type");
        Assert.assertTrue(ValueOfEmailType.equals("email"));
        Assert.assertTrue(ValueOfPasswordType.equals("password"));
    }

    @Test // Testiramo
    /*
    Test 2: Koristeci Faker uneti nasumicno generisan email i password
    i verifikovati da se pojavljuje poruka "User does not exist".
*/
    public void testInputType() {
        WebElement login = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]/span"));
        login.click();
        WebElement emailInputField = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("email"))));
        System.out.println(emailInputField.getAttribute("type"));

        emailInputField.sendKeys(faker.internet().emailAddress());
        driver.findElement(By.id("password")).sendKeys(faker.internet().password());
        System.out.println("Ispis");
        System.out.println(driver.findElement(By.id("password")).getText());
        Assert.assertEquals(emailInputField.getText(), driver.findElement(By.id("password")).getText());
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button")).submit();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li")).getText().equals("User does not exists"));
    }

    /*
    Test 3: Verifikovati da kad se unese admin@admin.com (sto je dobar email)
    i pogresan password (generisan faker-om), da se pojavljuje poruka "Wrong password"
     */
    @Test
    public void testLogin() {
        // Dobavljamo link.
        WebElement login = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/header/div/div[3]/a[3]/span"));
        //Klikcemo na njega
        login.click();
        //Idemo na stranicu sa input poljima gde ih selektujemo i unosimo korisnicko i sifru.

        WebElement emailInputField = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("email"))));
        emailInputField.sendKeys("admin@admin.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(faker.internet().password());
        //Selektujemo i klikcemo na dugme login
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[3]/span/form/div/div[3]/button"));
        loginButton.click();
        WebElement banerWrongPassword = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/main/div/div[2]/div/div/div[4]/div/div/div/div/div[1]/ul/li"));
        String porukaWrongPassword = banerWrongPassword.getText();
        Assert.assertEquals(porukaWrongPassword, "Wrong password");


    }


    @AfterMethod
    public void afterMethod() {


    }

    @AfterClass
    public void afterClass() throws InterruptedException {
        Thread.sleep(10000);
        driver.close();
        driver.quit();

    }


}
