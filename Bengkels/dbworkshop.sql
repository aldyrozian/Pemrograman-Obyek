-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2020 at 05:15 AM
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
  `kd_sp` varchar(20) NOT NULL,
  `jml_item` int(20) NOT NULL,
  `discount` int(20) NOT NULL,
  `jml_bayar` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `isi`
--

INSERT INTO `isi` (`no_services`, `kd_sp`, `jml_item`, `discount`, `jml_bayar`) VALUES
('SER001', 'SP002', 2, 5, 76000),
('SER002', 'SP002', 2, 5, 76000),
('SER003', 'SP002', 3, 5, 114000),
('SER004', 'SP002', 4, 5, 152000);

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
('SP001', 'Oli', 60000),
('SP002', 'Ban', 40000),
('SP003', 'Busi', 50000),
('SP004', 'Kampas Rem Belakang', 20000),
('SP005', 'Rem Kampas Atas', 20000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `isi`
--
ALTER TABLE `isi`
  ADD PRIMARY KEY (`no_services`);

--
-- Indexes for table `spareparts`
--
ALTER TABLE `spareparts`
  ADD PRIMARY KEY (`kd_sp`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
