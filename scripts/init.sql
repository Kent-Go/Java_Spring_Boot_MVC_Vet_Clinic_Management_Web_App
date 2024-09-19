-- Drop database
-- DROP DATABASE VetCare;

-- Create database
-- CREATE DATABASE IF NOT EXISTS VetCare;

-- Create Tables -- 
-- User --
CREATE TABLE IF NOT EXISTS user(
	id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	birth_date DATE NOT NULL,
	gender VARCHAR(10) NOT NULL,
	phone_number CHAR(10) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password TEXT NOT NULL
);
INSERT INTO user VALUES (1, 'John', 'Johnny Jr.', '2005-02-10', 'Male', '0126741127', 'littlejohn@gmail.com', 'galvanisedSquareSteel(hashed)'), (2, 'Jude', 'Bellingham', '1980-12-10', 'Male', '0782937410', 'j_bellingham@gmail.com', 'unknownHollowFeathers(hashed)'), (3, 'Susan', 'Smith', '1990-01-17', 'Female', '0453297849', 'ssmith@yahoo.com', 'hashed'), (4, 'Sarah', 'Thompson', '1979-04-20', 'Female', '0295783902', 'thompsonS@yahoo.com', 'hashedpassword');

-- Address --
CREATE TABLE IF NOT EXISTS address(
	id INT AUTO_INCREMENT PRIMARY KEY,
	street VARCHAR(255) NOT NULL,
	suburb VARCHAR(50) NOT NULL,
	state VARCHAR(30) NOT NULL,
	postcode CHAR(4) NOT NULL,
	user_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
INSERT INTO address VALUES (1, '1 Little John Court', 'Werribee', 'Victoria', '3030', 1), (2, '19 Arras Parade', 'Ryde', 'New South Wales', '2112', 2), (3, '2 Neath Street', 'Surrey Hills', 'Victoria', '3127', 3), (4, '186 Strong Avenue', 'Graceville', 'Queensland', '4075', 4);

-- Pet Owner --
CREATE TABLE IF NOT EXISTS pet_owner(
	id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
INSERT INTO pet_owner VALUES (1, 1), (2, 3);

-- Pet Owner Payment Details --
CREATE TABLE IF NOT EXISTS payment_details (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	card_number VARCHAR(19) NOT NULL,
	expiration_date VARCHAR(5) NOT NULL, -- Format: MM/YY
	hashed_cvv VARCHAR(255) NOT NULL,
	pet_owner_id INTEGER NOT NULL,
	FOREIGN KEY (pet_owner_id) REFERENCES pet_owner(id) ON DELETE CASCADE
);
INSERT INTO payment_details VALUES (1, 'John Doe', '1234 5678 1234 5678', '12/25', 'hashedCVV1', 1), (2, 'Jane Smith', '8765 4321 8765 4321', '01/26', 'hashedCVV2', 2);

-- Pet --
CREATE TABLE IF NOT EXISTS pet (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	birth_date DATE NOT NULL,
	species VARCHAR(255) NOT NULL,
	breed VARCHAR(255) NOT NULL,
	gender VARCHAR(6) NOT NULL,
	weight FLOAT NOT NULL,
    allergies VARCHAR(255),
    existing_conditions VARCHAR(255),
	pet_owner_id INTEGER NOT NULL,
	FOREIGN KEY (pet_owner_id) REFERENCES pet_owner(id) ON DELETE CASCADE
);
INSERT INTO pet VALUES (1, 'Rocky', '2024-05-31', 'Dog', 'Chihuahua', 'Male', 1.5, 'Dust', 'Arthritis',  1), (2, 'Luna', '2023-01-31', 'Dog', 'Chihuahua', 'Female', 2.1, 'Aloe Vera', 'Hip Dysplasia', 1), (3, 'Stella', '2023-01-31', 'Cat', 'Egyptian Mau', 'Female', 2.1, 'Pollens', 'Asthma', 2);

-- Immunisation History --
CREATE TABLE IF NOT EXISTS immunisation_history (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	date DATE NOT NULL,
	notes VARCHAR(255),
	pet_id INTEGER NOT NULL,
	FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE
);
INSERT INTO immunisation_history VALUES (1, 'HPV', '2023-12-14', '', 1), (2, 'Bordatella', '2023-12-14', '', 1), (3, 'Rabies', '2024-01-17', '', 2), (4, 'Lyme', '2024-01-17', '', 2);

-- Surgery History --
CREATE TABLE IF NOT EXISTS surgery_history (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	date DATE NOT NULL,
	notes VARCHAR(255),
	pet_id INTEGER NOT NULL,
	FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE
);
INSERT INTO surgery_history VALUES (1, 'Gastrointestinal Obstructions', '2023-12-14', 'No complications during surgery.', 1), (2, 'Hernia Repair', '2023-12-14', 'Laparoscopic procedure performed.', 2), (3, 'Gastropexy', '2024-01-16', 'Surgery to prevent future gastric dilatation.', 2), (4, 'ACL Repair Surgery', '2024-01-17', 'Patient advised on physiotherapy for recovery.', 2);

-- Vet --
CREATE TABLE IF NOT EXISTS vet(
	id INT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	languages_spoken VARCHAR(255) NOT NULL,
	self_description TEXT NOT NULL,
	profile_picture MEDIUMBLOB NOT NULL,
	user_id INT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
INSERT INTO vet VALUES (1, 'Dr.', 'English, Russian, Italian', "Basic description for a vet... uh... I'm very good at taking care of dogs and cats??", 'profilePicLink', 2), (2, 'Surgeon', 'English, Spanish', "I have very steady hands and is skillful with knifes on the operating room", 'profilePicLink', 4);

-- Qualification --
CREATE TABLE IF NOT EXISTS qualification(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	university VARCHAR(255) NOT NULL,
	country VARCHAR(255) NOT NULL,
	year YEAR NOT NULL,
	vet_id INT NOT NULL,
	FOREIGN KEY (vet_id) REFERENCES vet(id) ON DELETE CASCADE
);
INSERT INTO qualification VALUES (1, 'Bachelor of Veterinary Science (BVSc)', 'University of Melbourne', 'Australia', 1997, 1), (2, 'Master of Veterinary Science (MVSc)', 'University of Melbourne', 'Australia', 2002, 1);

-- Availability --
CREATE TABLE IF NOT EXISTS availability (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	date DATE NOT NULL,
	start_time TIME(0) NOT NULL,
	end_time TIME(0) NOT NULL
);
INSERT INTO availability VALUES (1, '2024-08-31', '13:00', '17:00'), (2, '2024-09-01', '10:00', '14:30'), (3, '2024-09-01', '09:30', '16:30');

-- Vet Availability --
CREATE TABLE IF NOT EXISTS vet_availability (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  vet_id INTEGER NOT NULL,
  availability_id INTEGER NOT NULL,
  FOREIGN KEY (vet_id) REFERENCES vet(id) ON DELETE CASCADE,
  FOREIGN KEY (availability_id) REFERENCES availability(id)
);
INSERT INTO vet_availability VALUES (1, 1, 1), (2, 1, 2), (3, 2, 2);

-- Medicine --
CREATE TABLE IF NOT EXISTS medicine (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	quantity INTEGER NOT NULL,
    price DOUBLE NOT NULL
);
INSERT INTO medicine VALUES (1, 'Antinol Plus EAB-277 For Dogs', 1000, 0.25), (2, 'Vetmedin 1.25mg Flavoured Chewable Tablets For Dogs', 2500, 0.1), (3, 'Clomicalm 80mg Tablets For Dogs and Cats', 3000, 1.2);

-- Appointment Type --
CREATE TABLE IF NOT EXISTS appointment_type (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	duration INTEGER NOT NULL,
	description TEXT NOT NULL
);
INSERT INTO appointment_type VALUES (1, 'General Clinical Consultation', 30, 'This service involves a comprehensive assessment of your pet’s overall health. The veterinarian will discuss any concerns you have, review your pet’s medical history, and provide recommendations for preventive care or treatment.'), (2, 'Physical Examination', 45, 'During a physical examination, the veterinarian will thoroughly check your pet’s body, including the eyes, ears, mouth, skin, and coat. This helps in identifying any signs of illness or abnormalities early on.'), (3, 'Dental Care', 30, 'Dental care services focus on maintaining your pet’s oral health. This includes teeth cleaning, polishing, and addressing any dental issues such as plaque buildup, gum disease, or tooth extractions if necessary.'), (4, 'Surgery', 90, 'Veterinary surgery encompasses a wide range of procedures, from routine spaying and neutering to more complex surgeries like tumor removal or orthopedic operations. These procedures are performed under anesthesia to ensure your pet’s comfort and safety.'), (5, 'Diet and Nutrition', 60, 'This service involves creating a balanced and nutritious diet plan tailored to your pet’s specific needs. The veterinarian will provide guidance on the best types of food, portion sizes, and any necessary supplements to ensure your pet’s optimal health.');

-- Appointment --
CREATE TABLE IF NOT EXISTS appointment (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	date DATE NOT NULL,
	start_time TIME(0) NOT NULL,
	end_time TIME(0) NOT NULL,
	vet_id INTEGER NOT NULL,
	pet_id INTEGER NOT NULL,
	appointment_type_id INTEGER NOT NULL,
	FOREIGN KEY (vet_id) REFERENCES vet(id) ON DELETE CASCADE,
	FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE,
	FOREIGN KEY (appointment_type_id) REFERENCES appointment_type(id)
);
INSERT INTO appointment VALUES (1, '2024-08-31', '13:00', '13:30', 1, 1, 1), (2, '2024-08-31', '11:00', '11:30', 1, 1, 1), (3, '2024-09-01', '15:00', '14:00', 2, 2, 2);

-- Vet Appointment Type Offered --
CREATE TABLE IF NOT EXISTS vet_appointment_type_offered(
	id INT AUTO_INCREMENT PRIMARY KEY,
    vet_id INTEGER NOT NULL,
    appointment_type_id INTEGER NOT NULL,
    FOREIGN KEY (vet_id) REFERENCES vet(id) ON DELETE CASCADE,
    FOREIGN KEY (appointment_type_id) REFERENCES appointment_type(id) ON DELETE CASCADE
);
INSERT INTO vet_appointment_type_offered VALUES (1, 1, 1), (2,1,2), (3,1,3), (4,1,5), (5,2,1), (6,2,3), (7,2,4), (8,2,5);

-- Order --
CREATE TABLE IF NOT EXISTS orders (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	date DATE NOT NULL,
	status VARCHAR(255) NOT NULL,
	payment_details_id INTEGER NOT NULL,
	FOREIGN KEY (payment_details_id) REFERENCES payment_details(id)
);
INSERT INTO orders VALUES (1, '2024-09-01', 'Delivered', 1), (2, '2024-09-02', 'Waiting for pickup', 1), (3, '2024-09-03', 'Packing order', 2);

-- Prescribed Medication --
CREATE TABLE IF NOT EXISTS prescribed_medication (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	dosage INTEGER NOT NULL,
	daily_frequency INTEGER NOT NULL,
	duration INTEGER NOT NULL,
	instruction TEXT NOT NULL,
	medicine_id INTEGER NOT NULL,
	order_id INTEGER NOT NULL, 
	appointment_id INTEGER NOT NULL,
	FOREIGN KEY (medicine_id) REFERENCES medicine(id),
	FOREIGN KEY (order_id) REFERENCES orders(id),
	FOREIGN KEY (appointment_id) REFERENCES appointment(id)
);
INSERT INTO prescribed_medication VALUES (1, 1, 3, 14, 'Feed during breakfast, lunch and dinner', 1, 2, 1), (2, 1, 2, 11, 'Swallow the pill along water. Feed during breakfast, and dinner', 2, 1, 1), (3, 1, 2, 14, 'Swallow the pill along food. Feed during breakfast, and dinner', 2, 1, 2);

-- Educational Videos --
CREATE TABLE IF NOT EXISTS educational_video (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	video_url VARCHAR(255) NOT NULL UNIQUE
);
INSERT INTO educational_video VALUES (1, 'Pets for Kids', 'https://www.youtube.com/watch?v=TmXGL4BorBw'), (2, 'Teaching Kids to Care for Pets | Videos for Toddlers', 'https://www.youtube.com/watch?v=pKosbOawGSY'), (3, 'A Day In The Life Of A Vet | If You See It, You Can Be It', 'https://www.youtube.com/watch?v=5XrzUiCLw3A');