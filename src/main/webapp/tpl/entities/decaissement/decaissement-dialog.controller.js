(function() {
    'use strict';

    angular
        .module('app')
        .controller('DecaissementDialogController', DecaissementDialogController);

    DecaissementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Decaissement','Caisse'];

    function DecaissementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Decaissement ,Caisse) {
        var vm = this;

        vm.decaissement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.caisses = Caisse.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.decaissement.id !== null) {
                Decaissement.update(vm.decaissement, onSaveSuccess, onSaveError);
            } else {
                Decaissement.save(vm.decaissement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:decaissementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateMouvement = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
