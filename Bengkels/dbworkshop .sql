-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 22, 2020 at 05:33 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbworkshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `isi`
--

CREATE TABLE `isi` (
  `no_services` varchar(20) NOT NULL,
  `nm_sp` varchar(20) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `discount` int(20) NOT NULL,
  `jml_bayar` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `isi`
--

INSERT INTO `isi` (`no_services`, `nm_sp`, `jumlah`, `discount`, `jml_bayar`) VALUES
('SER002', '1 Bulan', 2, 10, 90000);

-- --------------------------------------------------------

--
-- Table structure for table `rncsparepart`
--

CREATE TABLE `rncsparepart` (
  `rincian` varchar(100) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `nm_sp` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rncsparepart`
--

INSERT INTO `rncsparepart` (`rincian`, `jumlah`, `nm_sp`) VALUES
('1.Pengecekan Bulanan\r\n2.ganti Oli', 2, '1 Bulan'),
('1.pengecekan bulanan\r\n2.Ganti rem depan belakang\r\n3.ganti oli', 3, '2 Bulan'),
('pengecekan bulanan', 4, '5 bulan'),
('1.pengecekan full\r\n2.pembersihan injeksi\r\n3.ganti oli\r\n4.ganti aki\r\n5.ganti baut', 5, '10 bulan');

-- --------------------------------------------------------

--
-- Table structure for table `spareparts`
--

CREATE TABLE `spareparts` (
  `kd_sp` varchar(20) NOT NULL,
  `nm_sp` varchar(20) NOT NULL,
  `harga` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `spareparts`
--

INSERT INTO `spareparts` (`kd_sp`, `nm_sp`, `harga`) VALUES
('SP01', '1 Bulan', 100000),
('SP04', '10 bulan', 700000),
('SP02', '2 Bulan', 200000),
('SP03', '5 bulan', 500000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `isi`
--
ALTER TABLE `isi`
  ADD PRIMARY KEY (`no_services`),
  ADD KEY `nm_sp` (`nm_sp`),
  ADD KEY `jumlah` (`jumlah`);

--
-- Indexes for table `rncsparepart`
--
ALTER TABLE `rncsparepart`
  ADD PRIMARY KEY (`jumlah`),
  ADD KEY `nm_sp` (`nm_sp`);

--
-- Indexes for table `spareparts`
--
ALTER TABLE `spareparts`
  ADD PRIMARY KEY (`nm_sp`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rncsparepart`
--
ALTER TABLE `rncsparepart`
  ADD CONSTRAINT `rncsparepart_ibfk_1` FOREIGN KEY (`nm_sp`) REFERENCES `spareparts` (`nm_sp`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
