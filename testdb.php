<?php
$conn = new mysqli('localhost', 'admin', 'admin', 'costomerservice', 3307);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
echo "Connected successfully";
$conn->close();
?>
